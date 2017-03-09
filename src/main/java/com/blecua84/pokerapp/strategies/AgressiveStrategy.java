package com.blecua84.pokerapp.strategies;

import com.blecua84.pokerapp.game.actions.Strategy;
import com.blecua84.pokerapp.game.data.BetCommand;
import com.blecua84.pokerapp.game.data.BetCommandType;
import com.blecua84.pokerapp.game.data.GameInfo;
import com.blecua84.pokerapp.game.data.PlayerInfo;

/**
 * Estrategia agresiva de un jugador. Apuesta todas sus fichas cuando le toca.
 *
 * @author blecua84
 */
public class AgressiveStrategy implements Strategy {

    private final String name;

    public AgressiveStrategy(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BetCommand getCommand(GameInfo<PlayerInfo> state) {
        return new BetCommand(BetCommandType.ALL_IN);
    }
}
