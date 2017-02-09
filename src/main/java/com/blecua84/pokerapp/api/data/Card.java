package com.blecua84.pokerapp.api.data;

/**
 * Represents a card in the game.
 *
 * @author bleck84
 */
public final class Card {

    private static final String STRING_RANK_CARDS = "23456789TJQKA";

    private final Suit suit;

    private final Rank rank;

    public Card(Suit suit, Rank rank) {

        if(suit == null) {
            throw new IllegalArgumentException("suit no puede tener un valor nulo");
        }

        if(rank == null) {
            throw new IllegalArgumentException("rank no puede tener un valor nulo");
        }

        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        int rankValue = rank.ordinal();
        return STRING_RANK_CARDS.substring(rankValue, rankValue + 1) + suit.getC();
    }

    @Override
    public int hashCode() {
        return rank.ordinal() * Suit.values().length + suit.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = true;

        if(this != obj) {
            result = false;

            if(obj != null && getClass() == obj.getClass()) {
                result = hashCode() == obj.hashCode();
            }
        }

        return result;
    }
}
