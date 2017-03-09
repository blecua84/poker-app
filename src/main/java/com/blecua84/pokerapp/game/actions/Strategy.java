package com.blecua84.pokerapp.game.actions;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.game.data.PlayerInfo;
import com.blecua84.pokerapp.game.data.BetCommand;
import com.blecua84.pokerapp.game.data.GameInfo;

import java.util.List;
import java.util.Map;

/**
 * Interface de definición de la estrategia de un jugador.
 *
 * @author josejavier.blecua
 */
@FunctionalInterface
public interface Strategy {

    public String getName();

    public default BetCommand getCommand(GameInfo<PlayerInfo> state) {
        return null;
    }

    public default void updateState(GameInfo<PlayerInfo> state) {}

    public default void check(List<Card> communityCards) {}

    public default void onPlayerCommand(String player, BetCommand betCommand) {}

    public default void initHand(GameInfo<PlayerInfo> state) {
    }

    public default void endHand(GameInfo<PlayerInfo> state) {
    }

    public default void endGame(Map<String, Double> scores) {
    }
}
