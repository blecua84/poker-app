package com.blecua84.pokerapp.statemachine.states;

/**
 * Representa cada uno de los estados de la máquina.
 *
 * @author josejavier.blecua
 */
public interface State<T> {

    /**
     * Devuelve el nombre del estado.
     *
     * @return String
     */
    public String getName();

    /**
     * Realiza la ejecución de la lógica de estado.
     *
     * @param context Lógica de estado.
     * @return boolean con el resultado. Determina si la lógica del estado se ha ejecutado completamente o si se ha
     * quedado detenido en el estado a la espera de que algo lo vuelva a iniciar.
     */
    public boolean execute(T context);
}
