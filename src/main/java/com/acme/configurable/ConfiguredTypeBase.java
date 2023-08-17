package com.acme.configurable;

import com.acme.builder.RuntimeType;

/**
 * Base implementation of {@link ConfiguredType}.
 *
 * @param <T> config type
 */
public abstract class ConfiguredTypeBase<T extends ConfigType> implements ConfiguredType<T>, RuntimeType {

    private final T config;

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    protected ConfiguredTypeBase(ConfiguredPrototype<? extends T> prototype) {
        this(prototype.config());
    }

    /**
     * Create a new instance.
     *
     * @param config config
     */
    protected ConfiguredTypeBase(T config) {
        this.config = config;
    }

    @Override
    public T config() {
        return config;
    }
}
