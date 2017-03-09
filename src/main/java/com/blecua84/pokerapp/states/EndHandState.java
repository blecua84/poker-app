package com.blecua84.pokerapp.states;

import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.statemachine.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del estado EndHand.
 *
 * @author blecua84
 */
public class EndHandState implements State<ModelContext> {

    private static final String NAME = "EndHand";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean execute(ModelContext context) {
        PlayerEntity dealerPlayer = context.getPlayer(context.getDealer());
        List<PlayerEntity> players = context.getPlayers();
        List<PlayerEntity> nextPlayers = new ArrayList<>(players.size());
        int i = 0;
        int dealerIndex = 0;
        for (PlayerEntity p : players) {
            if (p.getChips() > 0) {
                nextPlayers.add(p);
                i++;
            }
            if (dealerPlayer == p) {
                dealerIndex = i-1;
            }
        }
        context.setDealer(dealerIndex);
        context.setPlayers(nextPlayers);
        return true;
    }
}
