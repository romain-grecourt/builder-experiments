package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.Configured;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ContentEncodingProvider;
import com.acme.demo.spi.MediaSupportProvider;

/**
 * {@link SocketListener} typed config.
 */
@Configured
public interface ListenerConfig extends ConfigType {

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
    ServiceProviderConfig<ContentEncodingProvider> contentEnding();

    /**
     * Media support configuration.
     *
     * @return media support configuration
     */
    ServiceProviderConfig<MediaSupportProvider> mediaSupport();
}
