package com.blecua84.pokerapp.dispatcher.impl;

import com.blecua84.pokerapp.dispatcher.GameEventDispatcher;
import com.blecua84.pokerapp.dispatcher.GameEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Clase de implementación de la interface GameEventDispatcher.
 *
 * @author blecua84
 */
public class GameEventDispatcherImpl<T> implements GameEventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(GameEventDispatcherImpl.class);

    public static final String EXIT_EVENT_TYPE = "exit";

    private final Map<String, GameEventProcessor<T>> processors;

    private final T target;

    private List<GameEvent> eventList = new ArrayList<>();

    private volatile boolean exit = false;

    private ExecutorService executorService;

    public GameEventDispatcherImpl(T target, Map<String, GameEventProcessor<T>> processors, ExecutorService executorService) {
        this.target = target;
        this.processors = processors;
        this.executorService = executorService;
    }

    @Override
    public synchronized void dispatch(GameEvent gameEvent) {
        eventList.add(gameEvent);
        this.notify();
    }

    @Override
    public synchronized void exit() {
        exit = true;
        this.notify();
    }

    @Override
    public void run() {
        while(!exit) {
            try {
                doTask();
            } catch (InterruptedException ex) {
                log.error("GameEventDispatcherImpl<", target, ex);
            }
        }
        executorService.shutdown();
    }

    private void doTask() throws InterruptedException {
        List<GameEvent> lastEvents;

        synchronized (this) {
            if(eventList.isEmpty()) {
                this.wait();
            }

            lastEvents = eventList;
            eventList = new ArrayList<>();
        }

        for(int i = 0; i < lastEvents.size(); i++) {
            GameEvent gameEvent = lastEvents.get(i);

            if(EXIT_EVENT_TYPE.equals(gameEvent.getType())) {
                exit = true;
            } else {
                //process(gameEvent);
            }
        }
    }
}
