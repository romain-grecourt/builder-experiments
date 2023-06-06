package com.acme.configurable;

/**
 * Mutable type-safe representation of the runtime configuration of a {@link ConfiguredType} object.
 * Provides access to the initial configuration with {@link #config()}.
 */
public interface RuntimeType<T extends ConfigType> {

    /**
     * Get the config view.
     *
     * @return config view
     */
    T config();
}
