package com.blecua84.pokerapp.game.evaluators;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.service.Combinatorial;
import com.blecua84.pokerapp.api.service.HandEvaluator;
import com.blecua84.pokerapp.api.service.impl.CombinatorialImpl;

import java.util.List;

import static com.blecua84.pokerapp.game.TexasHoldEmUtil.COMMUNITY_CARDS;
import static com.blecua84.pokerapp.game.TexasHoldEmUtil.PLAYER_CARDS;

/**
 * Algoritmo de evaluación para siete cartas.
 *
 * @author josejavier.blecua
 */
public class Hand7Evaluator {

    public static final int TOTAL_CARDS = PLAYER_CARDS + COMMUNITY_CARDS;
    private final int[] combinatorialBuffer = new int[COMMUNITY_CARDS];
    private final Combinatorial combinatorial = new CombinatorialImpl(COMMUNITY_CARDS, TOTAL_CARDS);
    private final HandEvaluator evaluator;
    private final Card[] evalBuffer = new Card[COMMUNITY_CARDS];
    private final Card[] cards = new Card[TOTAL_CARDS];
    private int communityCardsValue = 0;

    public Hand7Evaluator(HandEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setCommunityCards(List<Card> cc) {
        int i = 0;
        for(Card card: cc) {
            evalBuffer[i] = card;
            cards[i++] = card;
        }
        communityCardsValue = evaluator.eval(evalBuffer);
    }

    public int eval(Card card0, Card card1) {
        cards[COMMUNITY_CARDS] = card0;
        cards[COMMUNITY_CARDS + 1] = card1;

        return evalCards();
    }

    static Card[] copy(Card[] src, Card[] target, int[] positions) { int i = 0;
        for (int p : positions) {
            target[i++] = src[p];
        }
        return target;
    }

    private int evalCards() {
        combinatorial.clear();
        combinatorial.next(combinatorialBuffer);
        int result = communityCardsValue;

        while (combinatorial.hasNext()) {
            result = Math.max(result, evaluator.eval(
                    copy(cards, evalBuffer, combinatorial.next(combinatorialBuffer))));
        }

        return result;
    }
}
