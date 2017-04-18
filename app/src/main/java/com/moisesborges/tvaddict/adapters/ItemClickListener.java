package com.moisesborges.tvaddict.adapters;

/**
 * Created by moises.anjos on 18/04/2017.
 */
@FunctionalInterface
public interface ItemClickListener<T> {
    void consume(T item);
}
