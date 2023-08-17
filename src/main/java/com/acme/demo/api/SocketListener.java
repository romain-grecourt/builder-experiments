package com.acme.demo.api;

import java.net.InetAddress;

import io.helidon.common.context.Context;

import com.acme.configurable.ConfigType;
import com.acme.configurable.Configured;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ConfiguredPrototype;
import com.acme.configurable.ConfiguredTypeBase;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ContentEncodingProvider;
import com.acme.demo.spi.MediaSupportProvider;

/**
 * Socket listener.
 */
public class SocketListener extends ConfiguredTypeBase<SocketListener.TypedConfig> {

    private int port;

    /**
     * Create a new instance.
     *
     * @param prototype config
     */
    SocketListener(Prototype<?> prototype) {
        super(prototype);
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static SocketListenerBuilder builder() {
        return new SocketListenerBuilder();
    }

    /**
     * Get the effective port.
     *
     * @return port
     */
    public int port() {
        return port;
    }

    /**
     * {@link SocketListener} typed config.
     */
    @Configured
    public interface TypedConfig extends ConfigType {

        /**
         * Port.
         *
         * @return port
         */
        int port();

        /**
         * Host.
         *
         * @return host
         */
        String host();

        /**
         * Accept backlog.
         *
         * @return backlog
         */
        @ConfiguredOption("1024")
        int backlog();

        /**
         * Listener receive buffer size.
         *
         * @return receive buffer size
         */
        int receiveBufferSize();

        /**
         * Number of buffers queued for write operations.
         *
         * @return maximal number of queued writes
         */
        @ConfiguredOption("0")
        int writeQueueLength();

        /**
         * Initial buffer size in bytes of {@link java.io.BufferedOutputStream} created internally to
         * write data to a socket connection.
         *
         * @return initial buffer size used for writing
         */
        @ConfiguredOption("512")
        int writeBufferSize();

        /**
         * Maximal number of bytes an entity may have.
         *
         * @return maximal number of bytes of entity, or {@code -1} if unlimited
         */
        @ConfiguredOption("-1")
        int maxPayloadSize();

        /**
         * Content encoding configuration.
         *
         * @return content encoding configuration
         */
        ServiceProviderConfig<ContentEncodingProvider> contentEncoding();

        /**
         * Media support configuration.
         *
         * @return media support configuration
         */
        ServiceProviderConfig<MediaSupportProvider> mediaSupport();
    }

    /**
     * {@link SocketListener} prototype.
     */
    public interface Prototype<T extends TypedConfig> extends ConfiguredPrototype<T> {

        @Option(initializer = "io.helidon.common.Context::create")
        Context context();

        @Alias(value = "host", resolver = "java.net.InetAddress::getByName")
        InetAddress bindAddress();
    }
}
