package com.blecua84.pokerapp.api;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.data.Rank;
import com.blecua84.pokerapp.api.data.Suit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase principal de nuestro juego de poker.
 *
 * @author josejavier.blecua
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static void insert(Set<Card> cards, Card card) {
        if(!cards.contains(card)) {
            LOGGER.debug("Insertamos la carta: {}", card);
            cards.add(card);
        } else {
            LOGGER.debug("La carta {} ya está insertada.", card);
        }
    }

    public static void main(String[] args) {
        Set<Card> cards = new HashSet<>();

        Card[] cards2Insert = {
                new Card(Suit.CLUB, Rank.ACE),
                new Card(Suit.CLUB, Rank.TWO),
                new Card(Suit.CLUB, Rank.THREE),
                new Card(Suit.CLUB, Rank.ACE),
        };


        for(Card card: cards2Insert) {
            insert(cards, card);
        }
    }
}
