package com.blecua84.pokerapp.game.config;

/**
 * Configuration game.
 *
 * @author josejavier.blecua
 */
public class Settings {

    private int maxPlayers;
    private long time;
    private int maxErrors;
    private int maxRounds;
    private long playerChip;
    private long smallBind;
    private int rounds4IncrementBlind;

    public Settings() {}

    public Settings(Settings s) {
        this.maxPlayers = s.maxPlayers;
        this.time = s.time;
        this.maxErrors = s.maxErrors;
        this.maxRounds = s.maxRounds;
        this.playerChip = s.playerChip;
        this.smallBind = s.smallBind;
        this.rounds4IncrementBlind = s.rounds4IncrementBlind;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public void setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public long getPlayerChip() {
        return playerChip;
    }

    public void setPlayerChip(long playerChip) {
        this.playerChip = playerChip;
    }

    public long getSmallBind() {
        return smallBind;
    }

    public long getBigBind() {
        return smallBind * 2;
    }

    public void setSmallBind(long smallBind) {
        this.smallBind = smallBind;
    }

    public int getRounds4IncrementBlind() {
        return rounds4IncrementBlind;
    }

    public void setRounds4IncrementBlind(int rounds4IncrementBlind) {
        this.rounds4IncrementBlind = rounds4IncrementBlind;
    }
}
