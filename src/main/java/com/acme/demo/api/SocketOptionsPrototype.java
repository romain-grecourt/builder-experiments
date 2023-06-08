package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

import java.time.Duration;

/**
 * Prototype for {@link SocketOptions}.
 */
public interface SocketOptionsPrototype extends ConfiguredPrototype<SocketOptionsConfig> {

    /**
     * Read timeout.
     *
     * @return read timeout
     */
    Duration readTimeout();

    /**
     * Connect timeout.
     *
     * @return connect timeout
     */
    Duration connectTimeout();
}
