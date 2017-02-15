package com.blecua84.pokerapp.dispatcher;

import com.blecua84.pokerapp.dispatcher.impl.GameEvent;

/**
 * Interface de definición del repartidor de eventos.
 *
 * @author blecua84
 */
public interface GameEventDispatcher extends Runnable {

    public void dispatch(GameEvent gameEvent);

    public void exit();
}
