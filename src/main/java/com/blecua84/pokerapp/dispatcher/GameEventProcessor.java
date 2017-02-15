package com.blecua84.pokerapp.dispatcher;

import com.blecua84.pokerapp.dispatcher.impl.GameEvent;

/**
 * Interface de definición del procesador de eventos.
 *
 * @author blecua84
 */
@FunctionalInterface
public interface GameEventProcessor<T> {

    public void process(T target, GameEvent gameEvent);
}
