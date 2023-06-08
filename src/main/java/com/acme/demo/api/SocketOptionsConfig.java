package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredOption;

public interface SocketOptionsConfig extends ConfigType {

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
