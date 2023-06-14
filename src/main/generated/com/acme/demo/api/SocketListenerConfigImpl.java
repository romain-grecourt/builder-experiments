package com.acme.demo.api;

import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ContentEncodingProvider;
import com.acme.demo.spi.MediaSupportProvider;

/**
 * {@link SocketListener.TypedConfig} implementation.
 */
public class SocketListenerConfigImpl implements SocketListener.TypedConfig {

    private final int port;
    private final String host;
    private final int backlog;
    private final int receiveBufferSize;
    private final int writeQueueLength;
    private final int writeBufferSize;
    private final int maxPayloadSize;
    private final ServiceProviderConfig<ContentEncodingProvider> contentEncoding;
    private final ServiceProviderConfig<MediaSupportProvider> mediaSupport;

    protected SocketListenerConfigImpl(SocketListenerBuilderBase<?> builder) {
        this.port = builder.port();
        this.host = builder.host();
        this.backlog = builder.backlog();
        this.receiveBufferSize = builder.receiveBufferSize();
        this.writeBufferSize = builder.writeBufferSize();
        this.writeQueueLength = builder.writeQueueLength();
        this.maxPayloadSize = builder.maxPayloadSize();
        this.contentEncoding = builder.contentEncoding();
        this.mediaSupport = builder.mediaSupport();
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public int backlog() {
        return backlog;
    }

    @Override
    public int receiveBufferSize() {
        return receiveBufferSize;
    }

    @Override
    public int writeQueueLength() {
        return writeQueueLength;
    }

    @Override
    public int writeBufferSize() {
        return writeBufferSize;
    }

    @Override
    public int maxPayloadSize() {
        return maxPayloadSize;
    }

    @Override
    public ServiceProviderConfig<ContentEncodingProvider> contentEncoding() {
        return contentEncoding;
    }

    @Override
    public ServiceProviderConfig<MediaSupportProvider> mediaSupport() {
        return mediaSupport;
    }
}
