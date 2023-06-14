package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ConfiguredPrototype;
import com.acme.configurable.ConfiguredTypeBase;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.net.SocketOption;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.net.StandardSocketOptions.SO_KEEPALIVE;
import static java.net.StandardSocketOptions.SO_RCVBUF;
import static java.net.StandardSocketOptions.SO_REUSEADDR;
import static java.net.StandardSocketOptions.SO_SNDBUF;
import static java.net.StandardSocketOptions.TCP_NODELAY;

/**
 * Socket options.
 */
public class SocketOptions extends ConfiguredTypeBase<SocketOptions.TypedConfig> {

    /**
     * {@link SocketOptions} typed configuration.
     */
    public interface TypedConfig extends ConfigType {

        /**
         * Read timeout in seconds.
         *
         * @return read timeout in seconds
         */
        @ConfiguredOption(value = "30", key = "read-timeout-seconds")
        int readTimeout();

        /**
         * Connect timeout in seconds.
         *
         * @return connect timeout in seconds
         */
        @ConfiguredOption(value = "10", key = "connect-timeout-seconds")
        int connectTimeout();

        /**
         * Value of {@link java.net.StandardSocketOptions#SO_SNDBUF}.
         *
         * @return send buffer size, in bytes
         */
        @ConfiguredOption("32768")
        int sendBufferSize();

        /**
         * Value of {@link java.net.StandardSocketOptions#SO_RCVBUF}.
         *
         * @return receive buffer size, in bytes
         */
        @ConfiguredOption("32768")
        int receiveBufferSize();

        /**
         * Whether to use {@link java.net.StandardSocketOptions#SO_KEEPALIVE}.
         *
         * @return {@code true} if used
         */
        boolean keepAlive();

        /**
         * Whether to reuse address.
         *
         * @return {@code true} if re-used.
         */
        boolean reuseAddress();

        /**
         * Whether to use {@link java.net.StandardSocketOptions#TCP_NODELAY}.
         *
         * @return {@code true} if used
         */
        @ConfiguredOption("false")
        boolean tcpNoDelay();
    }

    /**
     * Prototype for {@link SocketOptions}.
     */
    public interface SocketOptionsPrototype extends ConfiguredPrototype<TypedConfig> {

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

    @SuppressWarnings("rawtypes")
    private final Map<SocketOption, Object> socketOptions = new HashMap<>();
    private final Duration connectTimeout;
    private final Duration readTimeout;

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    protected SocketOptions(SocketOptionsPrototype prototype) {
        super(prototype);
        TypedConfig config = prototype.config();
        socketOptions.put(SO_RCVBUF, config.receiveBufferSize());
        socketOptions.put(SO_SNDBUF, config.sendBufferSize());
        socketOptions.put(SO_REUSEADDR, config.reuseAddress());
        socketOptions.put(SO_KEEPALIVE, config.keepAlive());
        socketOptions.put(TCP_NODELAY, config.tcpNoDelay());
        connectTimeout = prototype.connectTimeout();
        readTimeout = prototype.readTimeout();
    }

    /**
     * Get the connect timeout.
     *
     * @return connect timeout
     */
    public Duration connectTimeout() {
        return connectTimeout;
    }

    /**
     * Get the read timeout.
     *
     * @return read timeout
     */
    public Duration readTimeout() {
        return readTimeout;
    }

    /**
     * Configure socket with defined socket options.
     *
     * @param socket socket to update
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void configureSocket(Socket socket) {
        for (Map.Entry<SocketOption, Object> entry : socketOptions.entrySet()) {
            try {
                socket.setOption(entry.getKey(), entry.getValue());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
