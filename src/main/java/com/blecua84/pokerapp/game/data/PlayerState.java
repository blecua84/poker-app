package com.blecua84.pokerapp.game.data;

/**
 * Created by kairosbleck on 8/2/17.
 */
public enum PlayerState {
    READY(true),
    OUT(false),
    FOLD(false),
    CHECK(true),
    CALL(true),
    RAISE(true),
    ALL_IN(false);

    private final boolean active;

    private PlayerState(boolean isActive) {
        this.active = isActive;
    }

    public boolean isActive() {
        return active;
    }
}
