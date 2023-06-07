package com.acme.configurable;

/**
 * A type that is configured with a {@link ConfigType}.
 *
 * @param <T> config type
 * @see ConfigType
 */
public interface ConfiguredType<T extends ConfigType> {

    /**
     * Get the configuration.
     *
     * @return config
     */
    T config();
}
