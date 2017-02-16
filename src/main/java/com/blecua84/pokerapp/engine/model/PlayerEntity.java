package com.blecua84.pokerapp.engine.model;

import com.blecua84.pokerapp.game.data.BetCommand;
import com.blecua84.pokerapp.game.data.PlayerInfo;

/**
 * Clase de datos de un jugador.
 *
 * @author blecua84
 */
public class PlayerEntity extends PlayerInfo {

    private int handValue = 0;
    private BetCommand betCommand;
    private boolean showCards;

    public PlayerEntity() { }

    public boolean isShowCards() {
        return showCards;
    }

    public void setShowCards(boolean showCards) {
        this.showCards = showCards;
    }

    public BetCommand getBetCommand() {
        return betCommand;
    }

    public void setBetCommand(BetCommand betCommand) {
        this.betCommand = betCommand;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }
}
