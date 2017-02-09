package com.blecua84.pokerapp.statemachine.data;

import com.blecua84.pokerapp.statemachine.states.Checker;
import com.blecua84.pokerapp.statemachine.states.State;

/**
 * Clase que representa una transición. Sus elementos serán los siguientes:
 *  - Estado de inicio.
 *  - Estado final.
 *  - Clase que permite realizar la comprobación que determinará si esa es la transición elegida.
 */
public class Transition<T> {

    // Estado de origen
    private final State<T> origin;

    // Estado final
    private final State<T> target;

    // Clase que realizará la comprobación
    private final Checker<T> checker;

    /**
     * Constructor que recibirá los siguientes parámetros:
     *
     * @param origin Estado de origen.
     * @param target Estado final.
     * @param checker Clase que realizará la comprobación.
     */
    public Transition(State<T> origin, State<T> target, Checker<T> checker) {
        this.origin = origin;
        this.target = target;
        this.checker = checker;
    }

    /**
     * Obtiene el estado de origen.
     *
     * @return Estado de origen.
     */
    public State<T> getOrigin() {
        return origin;
    }

    /**
     * Obtiene el estado de destino.
     *
     * @return Estado de destino.
     */
    public State<T> getTarget() {
        return target;
    }

    /**
     * Retorna la clase que realizará la comprobación.
     *
     * @return Clase para realizar la comprobación.
     */
    public Checker<T> getChecker() {
        return checker;
    }
}
