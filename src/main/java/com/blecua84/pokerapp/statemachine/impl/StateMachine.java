package com.blecua84.pokerapp.statemachine.impl;

import com.blecua84.pokerapp.statemachine.data.Transition;
import com.blecua84.pokerapp.statemachine.states.Checker;
import com.blecua84.pokerapp.statemachine.states.State;

import java.util.*;

/**
 * Clase que modela la lógica de estados.
 *
 * @author josejavier.blecua
 */
public class StateMachine<T> {

    // Estado de inicio
    private State<T> initState = null;

    // Mapa de transiciones por defecto
    private final Map<String, State<T>> defaultTransition = new HashMap<>();

    // Mapa de transiciones
    private final Map<String, List<Transition<T>>> transitions = new HashMap<>();

    // Constructor
    public StateMachine() {}

    /**
     * Obtiene la lista de transiciones de un estado. Si no tiene transiciones definidas, se devolverá la lista vacía.
     *
     * @param state Estado de entrada.
     * @return Lista de transiciones definidas.
     */
    List<Transition<T>> getTransitionsByOrigin(State<T> state) {
        List<Transition<T>> resultTransitions = transitions.get(state.getName());

        if(resultTransitions == null) {
            resultTransitions = Collections.emptyList();
        }

        return resultTransitions;
    }

    /**
     * Establece el estado de inicio.
     *
     * @param initState Estado de inicio.
     */
    public void setInitState(State<T> initState) {
        this.initState = initState;
    }

    /**
     * Obtiene la transicion por defecto para un estado.
     *
     * @param origin Estado.
     * @return Transición por defecto al estado de entrada.
     */
    public State<T> getDefaultTransition(State<T> origin) {
        return this.defaultTransition.get(origin.getName());
    }

    /**
     * Establece la transición por defecto para un estado de origen.
     *
     * @param origin Estado de origen.
     * @param target Estado de destino.
     */
    public void setDefaultTransition(State<T> origin, State<T> target) {
        this.defaultTransition.put(origin.getName(), target);
    }

    /**
     * Añade una transición a la lista de transiciones interna.
     *
     * @param transition Transición a añadir.
     */
    public void addTransition(Transition<T> transition) {
        State<T> origin = transition.getOrigin();
        List<Transition<T>> listTransitions = transitions.get(origin.getName());

        if(listTransitions != null) {
            listTransitions = new ArrayList<>();
            transitions.put(origin.getName(), listTransitions);
        }

        listTransitions.add(transition);
    }

    /**
     * Añade una transición a la lista de transiciones interna a partir de los elementos que componen ésta.
     *
     * @param origin Estado de origen.
     * @param target Estado final.
     * @param checker Lógica de comprobación.
     */
    public void addTransition(State<T> origin, State<T> target, Checker<T> checker) {
        addTransition(new Transition<T>(origin, target, checker));
    }

    /**
     * Crea una instancia para la ejecución de la máquina de estados.
     *
     * @param data
     * @return
     */
    public StateMachineInstance<T> startInstance(T data) {
        return new StateMachineInstance(data, this, initState).execute();
    }
}
