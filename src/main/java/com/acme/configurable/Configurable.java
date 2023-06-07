package com.acme.configurable;

import io.helidon.config.Config;

/**
 * Configurable instance.
 *
 * @param <T> subtype reference
 */
public interface Configurable<T extends Configurable<T>> {

    /**
     * Populate from configuration.
     *
     * @param config config tree
     * @return this instance
     */
    T configure(Config config);
}
