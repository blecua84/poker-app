package com.blecua84.pokerapp.strategies;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.game.actions.Strategy;
import com.blecua84.pokerapp.game.data.*;

import java.util.List;
import java.util.Random;

/**
 * Estrategia de un jugador impredecible.
 *
 * @author blecua84
 */
public class RandomStrategy implements Strategy {

    private static final Random RAND = new Random();
    private final String name;
    private double aggressivity = 0.5 + RAND.nextDouble() / 2;
    private BetCommand lastBet = null;

    public RandomStrategy(String name) {
        this.name = "Random-" + name;
    }

    private void calcAggressivity(GameInfo<PlayerInfo> state, PlayerInfo player) {
        long allChips = state.getPlayers().stream()
                .filter(p -> p.isActive() ||
                    p.getState() == PlayerState.ALL_IN).mapToLong(
                    p -> p.getChips()).sum();
        long players = state.getPlayers().stream()
                .filter(p -> p.isActive() ||
                    p.getState() == PlayerState.ALL_IN && p.getChips() > 0).count();
        long myChips = player.getChips();
        double proportion = (allChips - myChips) / players;
        aggressivity = (myChips / (proportion + myChips)) / 2 + 0.70d;

        if (myChips > (allChips - myChips)) {
            aggressivity = 1.1;
        }
    }

    private long getMaxBet(GameInfo<PlayerInfo> state) {
        if (aggressivity > 1.d) {
            return Long.MAX_VALUE;
        }

        long players = state.getPlayers().stream().filter(p -> p.isActive() ||
                p.getState() == PlayerState.ALL_IN).count();
        double probability = 1.0D / players;
        long pot = state.getPlayers().stream().mapToLong(p -> p.getBet()).sum();

        return Math.round((probability * pot / (1 - probability)) * aggressivity);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BetCommand getCommand(GameInfo<PlayerInfo> state) {
        PlayerInfo ownInfo = state.getPlayer(state.getPlayerTurn());
        calcAggressivity(state, ownInfo);
        long otherPlayerMaxBet = state.getPlayers().stream().max(
                (p0, p1) -> Long.compare(p0.getBet(), p1.getBet())).get().getBet();
        long minBet = Math.max(otherPlayerMaxBet - ownInfo.getBet(),
                state.getSettings().getBigBind());
        long maxBet = getMaxBet(state);
        long chips = ownInfo.getChips();
        BetCommand result;
        if (minBet > maxBet) {
            result = new BetCommand(BetCommandType.FOLD);
        } else if (maxBet >= chips) {
            result = new BetCommand(BetCommandType.ALL_IN);
        } else if (maxBet > minBet &&
                (lastBet == null || lastBet.getType() != BetCommandType.RAISE)) {
            result = new BetCommand(BetCommandType.RAISE, maxBet);
        } else if (otherPlayerMaxBet==state.getSettings().getBigBind()||minBet==0) {
            result = new BetCommand(BetCommandType.CHECK);
        } else {
            result = new BetCommand(BetCommandType.CALL);
        }
        lastBet = result;
        return result;
    }

    @Override
    public void updateState(GameInfo<PlayerInfo> state) {
        this.lastBet = null;
    }

    @Override
    public void check(List<Card> communityCards) {
        this.lastBet = null;
    }

    @Override
    public String toString() {
        return String.join("{RandomStrategy-", name, "}");
    }
}
