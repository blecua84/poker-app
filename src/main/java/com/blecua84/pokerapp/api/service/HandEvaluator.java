package com.blecua84.pokerapp.api.service;

import com.blecua84.pokerapp.api.data.Card;

/**
 * Interface de definición de las operaciones a realizar para evaluar una posible jugada(mano).
 *
 * @author josejavier.blecua
 */
public interface HandEvaluator {

    /**
     * Recibe un array de cinco cartas y devuelve un número entero que constituirá el valor de la posible mano.
     *
     * @param cards Array de cinco cartas con la jugada(mano).
     * @return Valor numérico de la jugada(mano).
     */
    public int eval(Card[] cards);
}
