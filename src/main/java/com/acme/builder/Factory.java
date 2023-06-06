package com.acme.builder;

import java.util.function.Supplier;

/**
 * Pseudo factory.
 *
 * @param <T> target type
 */
public abstract class Factory<T> implements Builder<T> {

    private final Supplier<T> supplier;

    /**
     * Create a new instance.
     *
     * @param supplier supplier
     */
    protected Factory(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T build() {
        return supplier.get();
    }
}
