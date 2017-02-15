package com.blecua84.pokerapp.timer;

import com.blecua84.pokerapp.dispatcher.GameEventDispatcher;

/**
 * Definición de las operaciones del temporizador.
 *
 * @author blecua84
 */
public interface GameTimer extends Runnable {

    public void exit();

    public long getTime();

    public void resetTimer(Long timeroutId);

    public void setTime(long time);

    public GameEventDispatcher getDispatcher();

    public void setDispatcher(GameEventDispatcher gameEventDispatcher);
}
