package com.blecua84.pokerapp.statemachine.states;

/**
 * Interface que determina el comportamieto que se debe implementar para determinar qué transición será la siguiente
 * a seguir para alcanzar el próximo estado.
 *
 * @author josejavier.blecua
 */
@FunctionalInterface
public interface Checker<T> {

    /**
     * Ejecuta la lógica asociada a un estado y devuelve un valor boolean que determina si el estado ha concluido su
     * ejecución o por el contrario el estado se encuentra en modo pausa, esperando que un evento cambie el contexto y
     * dispare nuevamente la ejecución de la lógica de estado.
     *
     * @param context Lógica asociada a un estado.
     * @return boolean que determina si el estado ha concluido su ejecución.
     */
    public boolean check(T context);
}
