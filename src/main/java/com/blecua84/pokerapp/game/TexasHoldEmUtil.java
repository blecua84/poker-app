package com.blecua84.pokerapp.game;

import com.blecua84.pokerapp.game.data.BetCommandType;
import com.blecua84.pokerapp.game.data.PlayerState;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by kairosbleck on 8/2/17.
 */
public final class TexasHoldEmUtil {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 10;

    public static final int PLAYER_CARDS = 2;
    public static final int COMMUNITY_CARDS = 5;

    public static final Map<BetCommandType, PlayerState> PLAYER_STATE_CONVERSOR = buildPlayerStateConversor();

    private TexasHoldEmUtil() {}

    public static PlayerState convert(BetCommandType betCommandType) {
        return PLAYER_STATE_CONVERSOR.get(betCommandType);
    }

    private static Map<BetCommandType, PlayerState> buildPlayerStateConversor() {
        Map<BetCommandType, PlayerState> result = new EnumMap<>(BetCommandType.class);

        result.put(BetCommandType.FOLD, PlayerState.FOLD);
        result.put(BetCommandType.ALL_IN, PlayerState.ALL_IN);
        result.put(BetCommandType.CALL, PlayerState.CALL);
        result.put(BetCommandType.CHECK, PlayerState.CHECK);
        result.put(BetCommandType.RAISE, PlayerState.RAISE);
        result.put(BetCommandType.ERROR, PlayerState.FOLD);
        result.put(BetCommandType.TIMEOUT, PlayerState.FOLD);

        return result;
    }
}
