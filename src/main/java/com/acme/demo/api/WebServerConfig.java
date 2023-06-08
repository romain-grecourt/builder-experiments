package com.acme.demo.api;

import com.acme.configurable.Configured;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ServerConnectionSelector;

import java.util.Map;

/**
 * {@link WebServer} typed configuration.
 */
@Configured
public interface WebServerConfig extends SocketListenerConfig {

    /**
     * Host of the default socket. Defaults to all host addresses ({@code 0.0.0.0}).
     *
     * @return host address to listen on (for the default socket)
     */
    @ConfiguredOption("0.0.0.0")
    String host();

    /**
     * Port of the default socket.
     * If configured to {@code 0} (the default), server starts on a random port.
     *
     * @return port to listen on (for the default socket)
     */
    @ConfiguredOption("0")
    int port();

    /**
     * Sockets listeners configuration.
     *
     * @return map of {@link SocketListenerConfig} keyed by socket names
     */
    Map<String, SocketListenerConfig> sockets();

    /**
     * Indicate whether server threads should inherit inheritable thread locals.
     *
     * @return {@code true} if server threads should inherit inheritable thread locals
     */
    boolean inheritThreadLocals();

    /**
     * Connection providers configuration.
     *
     * @return connection providers configuration
     */
    ServiceProviderConfig<ServerConnectionSelector> connectionProviders();
}
