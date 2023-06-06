package com.acme.configurable;

/**
 * An object with initial and runtime configuration views.
 *
 * @param <T> config type
 * @param <U> runtime type
 * @see RuntimeType
 * @see ConfigType
 */
public interface ConfiguredType<T extends ConfigType, U extends RuntimeType<T>> {

    /**
     * Get the config view.
     *
     * @return config
     */
    default T config() {
        return runtime().config();
    }

    /**
     * Get the runtime view.
     *
     * @return runtime
     */
    U runtime();
}
