package com.blecua84.pokerapp.api.data;

/**
 * Represents the player's hand.
 *
 * @author bleck84
 */
public final class Hands {

    public static final int CARDS = 5;

    public enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH
    }

    private Hands() {

    }
}
