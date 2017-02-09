package com.blecua84.pokerapp.statemachine.states.impl;

import com.blecua84.pokerapp.statemachine.states.State;

/**
 * Estado basado en enteros.
 *
 * @author josejavier.blecua
 */
public class IntState implements State<Integer> {

    // Nombre del estado
    private String name;

    /**
     * Constructor.
     *
     * @param name Recibe el nombre del estado.
     */
    public IntState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean execute(Integer context) {
        return true;
    }
}
