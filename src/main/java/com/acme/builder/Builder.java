package com.acme.builder;

import java.util.function.Supplier;

/**
 * Builder.
 *
 * @param <T> target type
 */
public interface Builder<T> extends Supplier<T> {

    /**
     * Build the instance.
     *
     * @return instance
     */
    T build();

    @Override
    default T get() {
        return build();
    }
}
