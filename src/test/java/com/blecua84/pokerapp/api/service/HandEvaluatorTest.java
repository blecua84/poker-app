package com.blecua84.pokerapp.api.service;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.service.impl.HandEvaluatorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de test para la interface HandEvaluator.
 *
 * @author josejavier.blecua
 */
public class HandEvaluatorTest {

    // Inicialización de la implementación
    private HandEvaluator handEvaluator = new HandEvaluatorImpl();
    private Card[] cards;

    @Before
    public void setUp() throws Exception {
        cards = new Card[5];
    }

    private void pareja4() {
        cards[0] = new Card(Card.Suit.CLUB, Card.Rank.FOUR);
        cards[1] = new Card(Card.Suit.HEART, Card.Rank.KING);
        cards[2] = new Card(Card.Suit.SPADE, Card.Rank.THREE);
        cards[3] = new Card(Card.Suit.SPADE, Card.Rank.FOUR);
        cards[4] = new Card(Card.Suit.DIAMOND, Card.Rank.FIVE);
    }

    @Test
    public void eval_OK() {
        pareja4();

        Assert.assertNotNull(handEvaluator.eval(cards));
    }

}