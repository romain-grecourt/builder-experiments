package com.acme.demo.api;

import java.util.List;

import com.acme.configurable.Configured;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ConfiguredTypeBase;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ServerConnectionSelector;
import java.util.Map;

/**
 * WebServer !
 */
public class WebServer extends ConfiguredTypeBase<WebServer.TypedConfig> {

    /**
     * {@link WebServer} typed configuration.
     */
    @Configured
    public interface TypedConfig extends SocketListener.TypedConfig {

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
         * @return map of {@link SocketListener.TypedConfig} keyed by socket names
         */
        Map<String, SocketListener.TypedConfig> sockets();

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

    /**
     * Prototype for {@link WebServer}.
     */
    public interface Prototype extends SocketListener.Prototype<TypedConfig> {

        /**
         * Get the socket listeners.
         *
         * @return map of {@link SocketListener} keyed by socket names
         */
        Map<String, SocketListener> sockets();

        // TODO Map<String, Router> routers();

        /**
         * Get the content encoding context.
         *
         * @return content encoding context
         */
        ContentEncodingContext contentEncodingContext();

        /**
         * Get the media context.
         *
         * @return media context
         */
        MediaContext mediaContext();

        /**
         * Get the connection providers.
         *
         * @return list of {@link ServerConnectionSelector}
         */
        @Alias("connectionProviders")
        List<ServerConnectionSelector> connectionSelectors();
    }

    private final Map<String, SocketListener> sockets;
    private boolean started;

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    WebServer(Prototype prototype) {
        super(prototype);
        sockets = prototype.sockets();
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static WebServerBuilder builder() {
        return new WebServerBuilder();
    }

    /**
     * Get the socket listeners.
     *
     * @return map of socket listeners keyed by socket names
     */
    public Map<String, SocketListener> sockets() {
        return sockets;
    }

    /**
     * Start the server.
     *
     * @return this instance
     */
    public WebServer start() {
        started = true;
        return this;
    }

    /**
     * Indicate if the server is started.
     *
     * @return {@code true} if started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Stop the server.
     *
     * @return this instance
     */
    public WebServer stop() {
        started = false;
        return this;
    }
}
