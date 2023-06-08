package com.acme.demo.api;

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
public class SocketOptions extends ConfiguredTypeBase<SocketOptionsConfig> implements SocketOptionsPrototype {

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
        SocketOptionsConfig config = prototype.config();
        socketOptions.put(SO_RCVBUF, config.receiveBufferSize());
        socketOptions.put(SO_SNDBUF, config.sendBufferSize());
        socketOptions.put(SO_REUSEADDR, config.reuseAddress());
        socketOptions.put(SO_KEEPALIVE, config.keepAlive());
        socketOptions.put(TCP_NODELAY, config.tcpNoDelay());
        connectTimeout = prototype.connectTimeout();
        readTimeout = prototype.readTimeout();
    }

    @Override
    public Duration connectTimeout() {
        return connectTimeout;
    }

    @Override
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
