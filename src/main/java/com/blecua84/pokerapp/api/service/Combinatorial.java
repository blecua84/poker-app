package com.blecua84.pokerapp.api.service;

/**
 * Interface de definición de las operaciones que definen todas las combinaciones que se pueden dar en una mano.
 *
 * @author josejavier.blecua
 */
public interface Combinatorial {

    public long combinations();

    public int size();

    public void clear();

    public int[] next(int[] items);

    public boolean hasNext();
}
