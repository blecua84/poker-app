package com.blecua84.pokerapp.timer.impl;

import com.blecua84.pokerapp.dispatcher.GameEventDispatcher;
import com.blecua84.pokerapp.dispatcher.impl.GameEvent;
import com.blecua84.pokerapp.timer.GameTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * Implementación de la interface GameTimer.
 *
 * @author blecua84
 */
public class GameTimerImpl implements GameTimer {

    // Logger del sistema
    private static final Logger log = LoggerFactory.getLogger(GameTimerImpl.class);
    public static final String TIMEOUT_EVENT_TYPE = "timeOutCommand";

    private final String source;
    private long time;
    private GameEventDispatcher gameEventDispatcher;
    private boolean reset = false;
    private volatile boolean exit = false;
    private final ExecutorService executorService;
    private Long timeoutId;

    public GameTimerImpl(String source, ExecutorService executorService) {
        this.source = source;
        this.executorService = executorService;
    }

    @Override
    public synchronized void exit() {
        this.exit = true;
        this.reset = false;
        this.timeoutId = null;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public synchronized void resetTimer(Long timeoutId) {
        this.timeoutId = timeoutId;
        this.reset = true;
        notify();
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public synchronized GameEventDispatcher getDispatcher() {
        return gameEventDispatcher;
    }

    @Override
    public void setDispatcher(GameEventDispatcher gameEventDispatcher) {
        this.gameEventDispatcher = gameEventDispatcher;
    }

    @Override
    public void run() {
        log.debug("Init run...");

        while(!exit) {
            try {
                doTask();
            } catch(InterruptedException ex) {
                log.error("Timer interrupted", ex);
            }
        }

        log.debug("Finish run!!");
    }

    private synchronized void doTask() throws InterruptedException {
        if(timeoutId == null) {
            wait();
        }

        if(timeoutId != null) {
            reset = false;
            wait(time);

            if(!reset && timeoutId != null) {
                GameEvent gameEvent = new GameEvent(TIMEOUT_EVENT_TYPE, source, timeoutId);

                executorService.execute(() ->  gameEventDispatcher.dispatch(gameEvent));
                timeoutId = null;
            }
        }
    }
}
