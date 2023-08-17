package com.acme.demo.api;

import io.helidon.common.context.Context;
import io.helidon.config.Config;

import com.acme.builder.Builder;
import com.acme.configurable.Configurable;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ContentEncodingProvider;
import com.acme.demo.spi.MediaSupportProvider;

import java.net.InetAddress;

/**
 * Base builder for {@link SocketListener}.
 * This builder is suitable for inheritance by {@link com.acme.configurable.ConfiguredType} whose
 * {@link com.acme.configurable.ConfigType} inherits {@link SocketListener.TypedConfig}.
 *
 * @param <SELF> subtype reference
 */
public abstract class SocketListenerBuilderBase<SELF extends SocketListenerBuilderBase<SELF>>
        implements Configurable<SELF> {

    private int port = 0;
    private String host = "0.0.0.0";
    private int backlog = 1024;
    private int receiveBufferSize = 0;
    private int writeQueueLength = 0;
    private int writeBufferSize = 512;
    private int maxPayloadSize = -1;
    private ServiceProviderConfig<ContentEncodingProvider> contentEncoding;
    private ServiceProviderConfig<MediaSupportProvider> mediaSupport;

    private Context context;
    private InetAddress bindAddress;

    int port() {
        return port;
    }

    String host() {
        return host;
    }

    // config getters
    // visibility is package-private, only used by SocketListenerConfigImpl
    // which can be inherited so the getters don't need to be public

    int backlog() {
        return backlog;
    }

    int receiveBufferSize() {
        return receiveBufferSize;
    }

    int writeQueueLength() {
        return writeQueueLength;
    }

    int writeBufferSize() {
        return writeBufferSize;
    }

    int maxPayloadSize() {
        return maxPayloadSize;
    }

    ServiceProviderConfig<ContentEncodingProvider> contentEncoding() {
        return contentEncoding;
    }

    ServiceProviderConfig<MediaSupportProvider> mediaSupport() {
        return mediaSupport;
    }

    // prototype getters
    // visibility is package-private, only used by SocketListenerPrototypeImpl
    // which can be inherited so the getters don't need to be public

    Context context() {
        return context;
    }

    InetAddress bindAddress() {
        return bindAddress;
    }

    // ------------------------
    // builder fluent methods
    // ------------------------

    /**
     * Set the port.
     *
     * @param port port
     * @return this builder
     */
    public SELF port(int port) {
        this.port = port;
        return me();
    }

    /**
     * Set the host.
     *
     * @param host host
     * @return this builder
     */
    public SELF host(String host) {
        this.host = host;
        return me();
    }

    public SELF backlog(int backlog) {
        this.backlog = backlog;
        return me();
    }

    public SELF receiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
        return me();
    }

    public SELF writeQueueLength(int writeQueueLength) {
        this.writeQueueLength = writeQueueLength;
        return me();
    }

    public SELF writeBufferSize(int writeBufferSize) {
        this.writeBufferSize = writeBufferSize;
        return me();
    }

    public SELF maxPayloadSize(int maxPayloadSize) {
        this.maxPayloadSize = maxPayloadSize;
        return me();
    }

    public SELF contentEnding(ServiceProviderConfig<ContentEncodingProvider> contentEnding) {
        this.contentEncoding = contentEnding;
        return me();
    }

    public SELF mediaSupport(ServiceProviderConfig<MediaSupportProvider> mediaSupport) {
        this.mediaSupport = mediaSupport;
        return me();
    }

    public SELF context(Context context) {
        this.context = context;
        return me();
    }

    public SELF bindAddress(InetAddress bindAddress) {
        this.bindAddress = bindAddress;
        return me();
    }

    @Override
    public SELF configure(Config config) {
        config.get("host").asString().ifPresent(this::host);
        config.get("port").asInt().ifPresent(this::port);
        return me();
    }

    /**
     * Resolve values before {@link Builder#build()}.
     */
    // Note: resolve() is not part of an API to keep the visibility protected
    // We could introduce a base class, but there is little value
    protected void resolve() {
        try {
            if (bindAddress == null) {
                bindAddress = InetAddress.getByName(host());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    private SELF me() {
        return (SELF) this;
    }
}
