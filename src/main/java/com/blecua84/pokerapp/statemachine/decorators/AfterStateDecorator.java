package com.blecua84.pokerapp.statemachine.decorators;

import com.blecua84.pokerapp.statemachine.states.State;

/**
 * Clase que implementa el patrón Decorator para ejecutar código después de realizar la funcionalidad de un estado.
 *
 * @author josejavier.blecua
 */
public class AfterStateDecorator<T> implements State<T> {

    private final State<T> state;

    private final Runnable listener;

    public AfterStateDecorator(State<T> state, Runnable listener) {
        this.state = state;
        this.listener = listener;
    }

    @Override
    public String getName() {
        return this.state.getName();
    }

    @Override
    public boolean execute(T context) {
        boolean result = this.state.execute(context);

        if(result) {
            this.listener.run();
        }

        return result;
    }
}
