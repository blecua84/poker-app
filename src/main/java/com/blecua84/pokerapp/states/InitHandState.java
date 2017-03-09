package com.blecua84.pokerapp.states;

import com.blecua84.pokerapp.api.data.Deck;
import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.ModelUtil;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.game.config.Settings;
import com.blecua84.pokerapp.game.data.BetCommandType;
import com.blecua84.pokerapp.game.data.GameState;
import com.blecua84.pokerapp.game.data.PlayerState;
import com.blecua84.pokerapp.statemachine.states.State;

import java.util.List;

import static com.blecua84.pokerapp.game.TexasHoldEmUtil.MIN_PLAYERS;

/**
 * Implementa el estado inicial de la máquina de estados.
 *
 * @author blecua84
 */
public class InitHandState implements State<ModelContext> {

    private static final String NAME_STATE = "InitHand";

    @Override
    public String getName() {
        return NAME_STATE;
    }

    @Override
    public boolean execute(ModelContext context) {

        // Obtenemos la baraja y barajamos
        Deck deck = context.getDeck();
        deck.shuffle();

        // Configuracion de la partida
        Settings settings = context.getSettings();

        // Establecemos el estado inicial del juego
        context.setGameState(GameState.PRE_FLOP);

        // Quitamos las cartas comunitarias
        context.clearCommunityCard();

        // Establecemos la primera ronda de la partida
        context.setRound(context.getRound() + 1);

        if(context.getRound() % settings.getRounds4IncrementBlind() == 0) {
            settings.setSmallBind(2 * settings.getSmallBind());
        }

        context.setPlayersAllIn(0);
        context.setHighBet(0L);

        List<PlayerEntity> players = context.getPlayers();
        for(PlayerEntity player: players) {
            player.setState(PlayerState.READY);
            player.setHandValue(0);
            player.setBet(0);
            player.setShowCards(false);
            player.setCards(deck.obtainCard(), deck.obtainCard());
        }

        int numPlayers = context.getNumPlayers();
        context.setActivePlayers(numPlayers);

        int dealerIndex = (context.getDealer() + 1) % numPlayers;
        context.setDealer(dealerIndex);
        context.setPlayerTurn((dealerIndex + 1) % numPlayers);

        if(numPlayers > MIN_PLAYERS) {
            compulsoryBet(context, settings.getSmallBind());
        }
        compulsoryBet(context, settings.getBigBind());

        return true;
    }

    private void compulsoryBet(ModelContext context, long chips) {
        int turn = context.getPlayerTurn();
        PlayerEntity playerEntity = context.getPlayer(turn);
        if(playerEntity.getChips() < chips) {
            playerEntity.setState(PlayerState.ALL_IN);
            ModelUtil.playerBet(context, playerEntity, BetCommandType.ALL_IN, playerEntity.getChips());
        } else {
            ModelUtil.playerBet(playerEntity, chips);
        }
        context.setHighBet(chips);
        context.setPlayerTurn((turn + 1) % context.getNumPlayers());
    }
}
