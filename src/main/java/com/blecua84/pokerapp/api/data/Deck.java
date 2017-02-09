package com.blecua84.pokerapp.api.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que define el comportamiento de una baraja.
 *
 * @author josejavier.blecua
 */
public class Deck {

    private final List<Card> cards;

    private int index = 0;

    public Deck () {
        this.cards = getAllCards();
    }

    /**
     * Método que mezcla las cartas y vuelve a posicionar el índice en la primera posición de la baraja.
     */
    public void shuffle() {
        this.index = 0;
        Collections.shuffle(cards);
    }

    /**
     * Obtiene la carta de la baraja que le indica el atributo index. Si index es mayor que el número de cartas
     * de la baraja, el método devolverá la carta null, puesto que ya no hay cartas disponibles.
     *
     * @return Card
     */
    public Card obtainCard() {
        Card cardToObtain = null;

        if(index < cards.size()) {
            cardToObtain = cards.get(index);
            index++;
        }

        return cardToObtain;
    }

    private static List<Card> getAllCards() {
        int numCards = Card.Suit.values().length * Card.Rank.values().length;

        List<Card> cardList = new ArrayList<>(numCards);
        for(Card.Suit suit: Card.Suit.values()) {
            for(Card.Rank rank: Card.Rank.values()) {
                cardList.add(new Card(suit, rank));
            }
        }

        return cardList;
    }
}
