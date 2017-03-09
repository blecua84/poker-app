package com.blecua84.pokerapp.cucumber;

import com.blecua84.pokerapp.api.data.Card;
import com.blecua84.pokerapp.api.data.Hands;
import com.blecua84.pokerapp.api.data.Rank;
import com.blecua84.pokerapp.api.data.Suit;
import com.blecua84.pokerapp.api.service.HandEvaluator;
import com.blecua84.pokerapp.api.service.impl.HandEvaluatorImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * Clase de test que enlaza la funcionalidad definida en la feature HandEvaluator.feature con nuestro componente
 * implementado.
 *
 * @author josejavier.blecua
 */
public class HandEvaluatorSteps {

    private static final String[] VALORES = {"mano0", "iguales", "mano1"};
    private HandEvaluator handEvaluator;
    private String resultado;

    @Given("^un HandEvaluator")
    public void un_HandEvaluator() throws Throwable {
        handEvaluator = new HandEvaluatorImpl();
    }

    @When("^calculamos la comparación entre (.*) y (.*)$")
    public void calculamos_la_comparacion(String h0, String h1) throws Throwable {
        int evalhand0 = handEvaluator.eval(fromString2Cards(h0));
        int evalhand1 = handEvaluator.eval(fromString2Cards(h1));
        int diferencia = evalhand1 - evalhand0;
        if (diferencia != 0) {
            diferencia = Math.abs(diferencia) / diferencia;
        }
        resultado = VALORES[diferencia + 1];
    }

    @Then("^el resultado esperado es (.*)$")
    public void el_resultado_esperado_es(String expResult) throws Throwable {
        assertEquals(expResult, resultado);
    }

    private static Card[] fromString2Cards(String hand) {
        Card[] cards = new Card[Hands.CARDS];

        if(hand != null) {
            String[] strHands = hand.split(" ");
            int index = 0;

            for(String card: strHands) {
                cards[index] = new Card(charToSuit(card.charAt(1)), charToRank(card.charAt(0)));

                index++;
            }
        }

        return cards;
    }

    private static Suit charToSuit(char cSuite) {
        Suit suit = null;

        switch (cSuite) {
            case '♠':
                suit = Suit.SPADE;
                break;
            case '♥':
                suit = Suit.HEART;
                break;
            case '♦':
                suit = Suit.DIAMOND;
                break;
            case '♣':
                suit = Suit.CLUB;
                break;
            default:
                break;
        }

        return suit;
    }

    private static Rank charToRank(char cRank) {
        Rank rank = null;

        switch (cRank) {
            case '2':
                rank = Rank.TWO;
                break;
            case '3':
                rank = Rank.THREE;
                break;
            case '4':
                rank = Rank.FOUR;
                break;
            case '5':
                rank = Rank.FIVE;
                break;
            case '6':
                rank = Rank.SIX;
                break;
            case '7':
                rank = Rank.SEVEN;
                break;
            case '8':
                rank = Rank.EIGHT;
                break;
            case '9':
                rank = Rank.NINE;
                break;
            case 'T':
                rank = Rank.TEN;
                break;
            case 'J':
                rank = Rank.JACK;
                break;
            case 'Q':
                rank = Rank.QUEEN;
                break;
            case 'K':
                rank = Rank.KING;
                break;
            case 'A':
                rank = Rank.ACE;
                break;
            default:
                break;
        }

        return rank;
    }
}
