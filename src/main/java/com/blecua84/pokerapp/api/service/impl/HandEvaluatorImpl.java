package com.blecua84.pokerapp.api.service.impl;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.data.Hands;
import com.blecua84.pokerapp.api.exceptions.ExceptionUtil;
import com.blecua84.pokerapp.api.service.HandEvaluator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase de implementación de la interface HandEvaluator.
 *
 * @author josejavier.blecua
 */
public class HandEvaluatorImpl implements HandEvaluator {

    private static final int ENCODE_BASE = Card.Rank.ACE.ordinal() + 1;
    private static final int INDEXES_LENGTH = 2;
    private static final int RANK_INDEX = 0;
    private static final int REPEATS_INDEX = 1;
    private static final Hands.Type[][] MATRIX_TYPES = {
            {Hands.Type.HIGH_CARD},
            {Hands.Type.ONE_PAIR},
            {Hands.Type.TWO_PAIR},
            {Hands.Type.THREE_OF_A_KIND},
            {Hands.Type.FULL_HOUSE},
            {Hands.Type.FOUR_OF_A_KIND}
    };
    private final int[][] indexes = new int[Hands.CARDS][INDEXES_LENGTH];

    // Array que contiene todas las categorias de cartas
    private final int[] ranks = new int[ENCODE_BASE];

    // Array que contiene todos los palos existentes en la baraja
    private final int[] suits = new int[Card.Suit.values().length];

    // Flag para determinar si la jugada es "escalera"
    private boolean isStraight = false;

    // Flag para determinar si la jugada es "color"
    private boolean isFlush = false;

    private boolean isFlush(Card[] cards) {
        return suits[cards[0].getSuit().ordinal()] == Hands.CARDS;
    }

    @Override
    public int eval(Card[] cards) {
        ExceptionUtil.checkLengthArray(cards, "cards", Hands.CARDS);
        isFlush = false;

        // Inicializamos los arrays introduciendo el valor "0" en cada una de las posiciones mediante Arrays.fill
        Arrays.fill(suits, 0);
        Arrays.fill(ranks, 0);

        int index = 0;
        Set<Card> previousCards = new HashSet<>(Hands.CARDS);

        for(Card card: cards) {
            ExceptionUtil.checkNullArgument(card, "card[" + (index++) +"]");
            ExceptionUtil.checkArgument(previousCards.contains(card), "La carta {} está repetida.", card);

            previousCards.add(card);
            ranks[card.getRank().ordinal()]++;
            suits[card.getSuit().ordinal()]++;
        }
        isFlush = isFlush(cards);
        isStraight = false;
        int straightCounter = 0;
        int j = 0;
        for(int i = ranks.length - 1; i >= 0; i--) {
            if(ranks[i] > 0) {
                straightCounter++;
                isStraight = straightCounter == Hands.CARDS;
                indexes[j][RANK_INDEX] = i;
                indexes[j][REPEATS_INDEX] = ranks[i];
                upIndex(j++);
            } else {
                straightCounter = 0;
            }
        }

        isStraight = isStraight || checkStraight5toAce(straightCounter);
        return calculateHandValue();
    }

    // Actualiza el orden de los pares en los indices
    private void upIndex(int i) {
        int k = i;
        while(k > 0 && indexes[k - 1][REPEATS_INDEX] < indexes[k][REPEATS_INDEX]) {
            int[] temp = indexes[k - 1];
            indexes[k - 1] = indexes[k];
            indexes[k] = temp;
            k--;
        }
    }

    private boolean checkStraight5toAce(int straightCntr) {
        boolean isStraight5toAce = false;

        // Evaluamos si nos encontramos en el caso especial de la escalera al 5
        if(ranks[Card.Rank.ACE.ordinal()] == 1 && straightCntr == Hands.CARDS - 1) {
            // Si es el caso especial, hay que reorganizar los indices
            isStraight5toAce = true;
            for(int i = 1; i < indexes.length; i++) {
                indexes[i - 1][RANK_INDEX] = indexes[i][RANK_INDEX];
            }
            indexes[indexes.length - 1][RANK_INDEX] = Card.Rank.ACE.ordinal();
        }
        return isStraight5toAce;
    }

    private static int encodeValue(Hands.Type type, int[][] indexes) {
        int result = type.ordinal();
        int i = 0,j = 0;

        while(j < Hands.CARDS) {
            for(int k = 0; k < indexes[i][REPEATS_INDEX]; k++) {
                result *= ENCODE_BASE + indexes[i][RANK_INDEX];
                j++;
            }
            i++;
        }
        return  result;
    }

    private int calculateHandValue() {
        Hands.Type type;

        if (isStraight) {
            type = isFlush ? Hands.Type.STRAIGHT_FLUSH : Hands.Type.STRAIGHT;
        } else if(isFlush) {
            type = Hands.Type.FLUSH;
        } else {
            type = MATRIX_TYPES[indexes[0][REPEATS_INDEX] - 1][indexes[1][REPEATS_INDEX] - 1];
        }

        return encodeValue(type, indexes);
    }
}
