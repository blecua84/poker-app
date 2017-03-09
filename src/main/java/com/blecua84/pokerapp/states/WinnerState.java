package com.blecua84.pokerapp.states;

import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.game.data.PlayerState;
import com.blecua84.pokerapp.statemachine.states.State;

import java.util.List;

/**
 * Clase que implementa el estado que determina el ganador de la partida.
 *
 * @author blecua84
 */
public class WinnerState implements State<ModelContext> {

    private static final String NAME = "Winner";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean execute(ModelContext context) {
        List<PlayerEntity> players = context.getPlayers();

        players.stream()
                .filter(p -> p.isActive() || p.getState() == PlayerState.ALL_IN)
                .findFirst()
                .get()
                .addChips(players
                            .stream()
                            .mapToLong(p -> p.getBet())
                            .sum());

        return true;
    }
}
