package com.blecua84.pokerapp.states.ifaces;

import com.blecua84.pokerapp.engine.model.ModelContext;
import com.blecua84.pokerapp.engine.model.PlayerEntity;
import com.blecua84.pokerapp.game.data.BetCommand;

/**
 * BetChecker interface definition.
 *
 * @author blecua84
 */
@FunctionalInterface
public interface BetChecker {

    public boolean check(ModelContext m, PlayerEntity player, BetCommand bet);
}
