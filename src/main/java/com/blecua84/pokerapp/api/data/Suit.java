package com.blecua84.pokerapp.api.data;

/**
 * Expresa los palos de la baraja.
 *
 * @author josejavier.blecua
 */
public enum Suit {
    SPADE('♠'), HEART('♥'), DIAMOND('♦'), CLUB('♣');

    private Suit(char c) {
        this.c = c;
    }

    private final char c;

    public char getC() {
        return this.c;
    }
}
