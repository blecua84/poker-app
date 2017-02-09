package com.blecua84.pokerapp.api.data;

import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;


/**
 * Created by bleck84 on 4/1/16.
 */
public class CardTest extends TestCase {

    private static Card[] getAllCards() {
        Card[] result = new Card[Card.Suit.values().length * Card.Rank.values().length];

        int i = 0;

        for(Card.Suit suit : Card.Suit.values()) {
            for(Card.Rank rank : Card.Rank.values()) {
                result[i] = new Card(suit, rank);
                i++;
            }
        }

        return result;
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode()");
        Card[] allCards = getAllCards();

        Set<Integer> hashCodes = new HashSet<>(allCards.length);

        for(Card card: allCards) {
            assertThat(hashCodes, not(CoreMatchers.hasItem(card.hashCode())));
        }
    }

    @Test
    public void testEqualsOtherObjects() {
        System.out.println("equalsOtherObjects");
        Card card = new Card(Card.Suit.CLUB, Card.Rank.ACE);
        assertNotEquals("card: " + card + " != null", card, null);
        assertNotEquals("card: " + card + " != 0", card, 0);
        assertNotEquals("card: " + card + " != \"2C\"", card, "2C");
    }

    @Test
    public void testEquals() {
        System.out.println("equals distinct");
        int i = 0;
        for(Card card0: getAllCards()) {
            int j = 0;
            for(Card card1: getAllCards()) {
                if(i == j) {
                    assertEquals(card0, card1);
                }
                j++;
            }
            i++;
        }
    }

    @Test
    public void testEqualsDistinct() {
        System.out.println("equals distinct");
        int i = 0;
        for(Card card0: getAllCards()) {
            int j = 0;
            for(Card card1: getAllCards()) {
                if(i != j) {
                    assertNotEquals(card0, card1);
                }
                j++;
            }
            i++;
        }
    }

    @Test
    public void testConstructor() {
       System.out.println("card()");
        Card.Suit expSuit = Card.Suit.CLUB;
        Card.Rank expRank = Card.Rank.TWO;

        Card instance = new Card(expSuit, expRank);
        Card.Suit suitResult = instance.getSuit();
        assertEquals(expSuit, suitResult);

        Card.Rank rankResult = instance.getRank();
        assertEquals(expRank, rankResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorSuitNull() {
        System.out.println("card(SuitNull)");

        try {
            Card.Suit expSuit = null;
            Card.Rank expRank = Card.Rank.TWO;
            Card instance = new Card(expSuit, expRank);
        } catch (IllegalArgumentException e) {
            System.out.println("card(SuitNull) --> Resultado esperado...");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRankNull() {
        System.out.println("card(RankNull)");

        try {
            Card.Suit expSuit = Card.Suit.CLUB;
            Card.Rank expRank = null;
            Card instance = new Card(expSuit, expRank);
        } catch (IllegalArgumentException e) {
            System.out.println("card(RankNull) --> Resultado esperado...");
        }
    }



    @Test
    public void test(){
        String reg_pattern = "aaaaabbbbbbbbbccccdeeeeeee";

        String partialChar = "";
        String currentChar = "";
        StringBuffer compressStr = new StringBuffer();


        int count = 0;

        for(int i = 0; i < reg_pattern.length(); i++) {
            partialChar = reg_pattern.substring(i, i+1);

            if(currentChar.equals("")) {
                currentChar = partialChar;
                compressStr.append(currentChar);
            }

            if(partialChar.equals(currentChar)){
                count++;
            } else {
                if(count > 1)
                    compressStr.append(count);

                currentChar = partialChar;
                compressStr.append(currentChar);
                count = 1;
            }
        }

        if(count > 1)
            compressStr.append(count);

        System.out.println(compressStr);
    }

    public void test2() {
        int[] s = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        int[] lis = new int[s.length];
        int[] finalList = new int[s.length];

        lis[0] = 1;
        int count = 0;

        for(int i = 0; i < s.length; i++) {
            int max_n = 0;

            for(int j = 0; j < i; j++) {
                if(s[j] < s[i] && lis[j] > max_n) {
                    max_n = lis[j];
                    finalList[count] = lis[j];
                    count++;
                }
            }

            lis[i] = max_n + 1;
        }



        System.out.println(finalList.length);
    }

    public void test3() {
        int[] s = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        int[] lis = new int[s.length];

        lis[0] = 1;

        for(int i = 1; i < lis.length; i++) {
            int max_n = 0;

            for(int j = 0; j < i; j++) {
                if(s[j] < s[i] && lis[j] > max_n) {
                    max_n = lis[j];
                }
            }

            lis[i] = max_n + 1;
        }

        int max_i = 0;

        for(int i = 0; i < lis.length; i++) {
            if(lis[i] > max_i) {
                max_i = lis[i];
            }
        }

        System.out.println(max_i);
    }
}