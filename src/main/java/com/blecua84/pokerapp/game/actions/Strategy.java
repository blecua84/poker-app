package com.blecua84.pokerapp.game.actions;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.game.config.PlayerInfo;
import com.blecua84.pokerapp.game.data.BetCommand;
import com.blecua84.pokerapp.game.data.GameInfo;

import java.util.List;

/**
 * Interface de definición de la estrategia de un jugador.
 *
 * @author josejavier.blecua
 */
public interface Strategy {

    public String getName();

    public BetCommand getCommand(GameInfo<PlayerInfo> state);

    public default void updateState(GameInfo<PlayerInfo> state) {}

    public default void check(List<Card> communityCards) {}

    public default void onPlayerCommand(String player, BetCommand betCommand) {}
}
