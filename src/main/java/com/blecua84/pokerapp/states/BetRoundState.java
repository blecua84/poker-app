package com.blecua84.pokerapp.states;

import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.ModelUtil;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.game.TexasHoldEmUtil;
import com.blecua84.pokerapp.game.data.BetCommand;
import com.blecua84.pokerapp.game.data.BetCommandType;
import com.blecua84.pokerapp.game.data.PlayerState;
import com.blecua84.pokerapp.statemachine.states.State;
import com.blecua84.pokerapp.states.ifaces.BetChecker;

import java.util.EnumMap;
import java.util.Map;

/**
 * Implementación del estado de una ronda de apuestas.
 *
 * @author blecua84
 */
public class BetRoundState implements State<ModelContext> {

    private static final String NAME = "BetRound";

    private static final Map<BetCommandType, BetChecker> CHECKERS = buildBetCommandChecker();

    private static Map<BetCommandType,BetChecker> buildBetCommandChecker() {
        Map<BetCommandType, BetChecker> result = new EnumMap<>(BetCommandType.class);

        // Las apuestas de tipo FOLD son válidas
        result.put(BetCommandType.FOLD, (m, p, b) -> true);

        // Timeout o error son erroneas
        result.put(BetCommandType.TIMEOUT, (m, p, b) -> false);
        result.put(BetCommandType.ERROR, (m, p, b) -> false);

        // El resto de apuestas dependerá del estado de la partida
        // Para elevar la apuesta el jugador deberá tener fichas disponibles, que la apuesta sea más alta que la
        // anterior y que no sean todas las fichas del jugador.
        result.put(BetCommandType.RAISE, (m, p, b) ->
                b.getChips() > (m.getHighBet() - p.getBet()) &&
                        b.getChips() < p.getChips());

        // Para hacer un ALL_IN necesitamos todas las fichas del jugador "p"
        result.put(BetCommandType.ALL_IN, (m, p, b) -> {
            b.setChips(p.getChips());
            return p.getChips() > 0;
        });

        // Para un comando de tipo CALL hace falta igualar la cantidad de fichas
        result.put(BetCommandType.CALL, (c, p, b) -> {
            b.setChips(c.getHighBet() - p.getBet());
            return c.getHighBet() > c.getSettings().getBigBind();
        });

        // Un comando de tipo CHECK se da con las siguientes condiciones: o no hace falta apostar para continuar o que
        // únicamente haya que igualar la ciega grande.
        result.put(BetCommandType.CHECK, (c, p, b) -> {
            b.setChips(c.getHighBet() - p.getBet());
            return b.getChips() == 0 || c.getHighBet()==c.getSettings().getBigBind();
        });

        return result;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean execute(ModelContext context) {
        boolean result = false;
        int playerTurn = context.getPlayerTurn();
        PlayerEntity player = context.getPlayer(playerTurn);
        BetCommand command = player.getBetCommand();
        if (command != null) {
            BetCommand resultCommand = command;
            player.setBetCommand(null);
            long betChips = 0;
            BetCommandType commandType = command.getType();
            if (CHECKERS.get(commandType).check(context, player, command)) {
                betChips = command.getChips();
                player.setState(TexasHoldEmUtil.convert(command.getType()));
            } else {
                commandType = BetCommandType.FOLD;
                player.setState(PlayerState.FOLD);
                if (command.getType() == BetCommandType.TIMEOUT) {
                    resultCommand = new BetCommand(BetCommandType.TIMEOUT);
                } else {
                    resultCommand = new BetCommand(BetCommandType.ERROR);
                }
                ModelUtil.incrementErrors(player, context.getSettings());
            }
            ModelUtil.playerBet(context, player, commandType, betChips);
            context.lastResultCommand(player, resultCommand);
            context.setPlayerTurn(ModelUtil.nextPlayer(context, playerTurn));
            result = true;
        }
        return result;
    }
}
