package com.blecua84.pokerapp.api.service;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.data.Rank;
import com.blecua84.pokerapp.api.data.Suit;
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
        cards[0] = new Card(Suit.CLUB, Rank.FOUR);
        cards[1] = new Card(Suit.HEART, Rank.KING);
        cards[2] = new Card(Suit.SPADE, Rank.THREE);
        cards[3] = new Card(Suit.SPADE, Rank.FOUR);
        cards[4] = new Card(Suit.DIAMOND, Rank.FIVE);
    }

    @Test
    public void eval_OK() {
        pareja4();

        Assert.assertNotNull(handEvaluator.eval(cards));
    }

}