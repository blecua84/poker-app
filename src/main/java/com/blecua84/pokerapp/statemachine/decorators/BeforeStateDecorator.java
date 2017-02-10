package com.blecua84.pokerapp.statemachine.decorators;

import com.blecua84.pokerapp.statemachine.states.State;

/**
 * Clase que implementa el patrón Decorator para ejecutar código antes de realizar la funcionalidad de un estado.
 *
 * @author josejavier.blcua
 */
public class BeforeStateDecorator<T> implements State<T> {

    private final State<T> state;
    private final Runnable listener;
    private boolean executed = true;

    public BeforeStateDecorator(State<T> state, Runnable listener) {
        this.state = state;
        this.listener = listener;
    }


    @Override
    public String getName() {
        return state.getName();
    }

    @Override
    public boolean execute(T context) {
        if(executed) {
            listener.run();
        }
        executed = state.execute(context);
        return executed;
    }
}
