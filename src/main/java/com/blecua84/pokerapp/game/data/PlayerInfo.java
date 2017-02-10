package com.blecua84.pokerapp.game.data;

import javax.smartcardio.Card;

/**
 * Clase que contiene la información de cada jugador.
 *
 * @author josejavier.blecua
 */
public class PlayerInfo {

    private String name;
    private long chips;
    private long bet;
    private Card[] cards = new Card[2];
    private PlayerState state;
    private int errors;

    public PlayerInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChips() {
        return chips;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }

    public long getBet() {
        return bet;
    }

    public void setBet(long bet) {
        this.bet = bet;
    }

    public Card[] getCards() {
        return new Card[]{cards[0], cards[1]};
    }

    public void setCards(Card[] cards) {
        this.cards[0] = cards[0];
        this.cards[1] = cards[1];
    }

    public void setCards(Card card0, Card card1) { this.cards[0] = card0;
        this.cards[1] = card1;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public Card getCard(int index) {
        return cards[index];
    }
}
