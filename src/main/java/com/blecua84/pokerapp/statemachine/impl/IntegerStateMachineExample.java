package com.blecua84.pokerapp.statemachine.impl;

import com.blecua84.pokerapp.statemachine.states.impl.IntState;

/**
 * Ejemplo de maquina de estados de enteros.
 *
 * @author josejavier.blecua
 */
public class IntegerStateMachineExample {

    public static void main(String[] args) {
        StateMachine<Integer> sm = new StateMachine<>();

        // Definición de estados
        IntState intState1 = new IntState("State 1");
        IntState intState2 = new IntState("State 2");
        IntState intState3 = new IntState("State 3");
        IntState intState4 = new IntState("State 4");

        // Establecemos el estado inicial en la máquina de estados
        sm.setInitState(intState1);
        sm.addTransition(intState1, intState2, (n) -> (n % 2) == 0);
        sm.addTransition(intState1, intState3, (n) -> (n % 3) == 0);
        sm.setDefaultTransition(intState1, intState4);

        StateMachineInstance<Integer> smi = sm.startInstance(6) ;
    }
}
