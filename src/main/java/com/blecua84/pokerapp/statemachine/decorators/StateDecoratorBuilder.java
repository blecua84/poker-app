package com.blecua84.pokerapp.statemachine.decorators;

import com.blecua84.pokerapp.statemachine.states.State;

/**
 * Clase que implementa el patrón Builder para inicializar los decoradores.
 *
 * @author josejavier.blecua
 */
public class StateDecoratorBuilder<T> {

    private State<T> state;

    private StateDecoratorBuilder(State<T> state) {
        this.state = state;
    }

    public static <T> StateDecoratorBuilder<T> create(State<T> state) {
        return new StateDecoratorBuilder<T>(state);
    }

    public StateDecoratorBuilder<T> after(Runnable r) {
        this.state = new AfterStateDecorator<T>(state, r);
        return this;
    }

    public StateDecoratorBuilder<T> before(Runnable r) {
        this.state = new BeforeStateDecorator<T>(state, r);
        return this;
    }

    public State<T> build() {
        return state;
    }

    public static <T> State<T> after(State state, Runnable r) {
        return new AfterStateDecorator<T>(state, r);
    }

    public static <T> State<T> before(State<T> state, Runnable r) {
        return new BeforeStateDecorator<T>(state, r);
    }
}
