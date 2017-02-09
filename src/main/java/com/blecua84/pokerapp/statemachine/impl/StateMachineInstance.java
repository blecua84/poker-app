package com.blecua84.pokerapp.statemachine.impl;

import com.blecua84.pokerapp.statemachine.data.Transition;
import com.blecua84.pokerapp.statemachine.states.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Instancia de la máquina de estados.
 *
 * @author josejavier.blecua
 */
public class StateMachineInstance<T> {

    // System logger
    private static final Logger log = LoggerFactory.getLogger(StateMachine.class);

    private final T context;

    private final StateMachine<T> parent;

    private State<T> state;

    private boolean finish;
    private boolean pause;

    public StateMachineInstance(T context, StateMachine<T> parent, State state) {
        this.context = context;
        this.parent = parent;
        this.state = state;
        this.finish = false;
    }

    public boolean isFinish() {
        return this.finish;
    }

    public StateMachineInstance<T> execute() {
        log.info("Init execute...");
        this.pause = false;
        while(state != null && !pause) {
            state = executeState();
        }
        finish = state == null;

        if(finish)
            log.info("{} is finished!!", state.getName());

        log.info("End execute!!");
        return this;
    }

    public T getContext() {
        return context;
    }

    private State<T> executeState() {
        log.info("Init executeState for state {}...", state.getName());
        pause = !state.execute(context);

        if(pause)
            log.info("{} is paused...", state.getName());

        State<T> result = state;
        if(!pause) {
            List<Transition<T>> transitionList = parent.getTransitionsByOrigin(state);
            for (Transition<T> transition : transitionList) {
                if(transition.getChecker().check(context)) {
                    return transition.getTarget();
                }
            }
            result = parent.getDefaultTransition(state);
        }

        log.info("End executeState for state {}!!", state.getName());
        return result;
    }
}
