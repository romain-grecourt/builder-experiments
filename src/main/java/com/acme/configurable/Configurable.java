package com.acme.configurable;

import io.helidon.config.Config;

/**
 * A fluent mixin for configurable types.
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
