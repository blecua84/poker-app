package com.blecua84.pokerapp.states;

import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.ModelUtil;
import com.blecua84.pokerapp.game.data.GameState;
import com.blecua84.pokerapp.game.data.PlayerState;
import com.blecua84.pokerapp.statemachine.states.State;

/**
 * Estado general del juego.
 *
 * @author blecua84
 */
public class CheckState implements State<ModelContext> {

    public static final String NAME = "Next";

    private static final GameState[] GAME_STATES = GameState.values();

    private static final int[] OBTAIN_CARDS = {3,1,1,0,0};

    @Override
    public String getName() {
        return NAME;
    }

    private int indexByGameState(GameState gameState) {
        int i = 0;

        while(i < GAME_STATES.length && GAME_STATES[i] != gameState) {
            i++;
        }

        return i;
    }

    @Override
    public boolean execute(ModelContext context) {
        int indexGameState = indexByGameState(context.getGameState());

        if(OBTAIN_CARDS[indexGameState] > 0) {
            context.addCommunityCards(OBTAIN_CARDS[indexGameState]);
        }
        context.setGameState(GAME_STATES[indexGameState + 1]);
        context.setLastActivePlayers(context.getActivePlayers());
        context.setBets(0);
        context.getPlayers().stream().filter(p -> p.isActive()).
                forEach(p -> p.setState(PlayerState.READY));
        context.setPlayerTurn(ModelUtil.nextPlayer(context, context.getDealer()));
        context.setLastBetCommand(null);
        context.setLastPlayerBet(null);

        return true;
    }
}
