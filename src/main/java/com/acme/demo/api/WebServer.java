package com.acme.demo.api;

import com.acme.configurable.ConfiguredType;

/**
 * WebServer !
 * This class is hand-crafted.
 */
public class WebServer implements ConfiguredType<WebServerConfig, WebServerRuntime> {

    private final WebServerRuntimeImpl runtime;
    private boolean started;

    private WebServer(WebServerRuntimeImpl runtime) {
        this.runtime = runtime;
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
     * Create a new default instance.
     *
     * @return new instance
     */
    public static WebServer create() {
        return builder().build();
    }

    /**
     * Create a new instance.
     *
     * @param config typed configuration
     * @return new instance
     */
    public static WebServer create(WebServerConfig config) {
        return new WebServer(new WebServerRuntimeImpl(config));
    }

    /**
     * Create a new instance.
     *
     * @param builder builder
     * @return new instance
     */
    public static WebServer create(WebServerBuilder builder) {
        return new WebServer(builder.buildRuntime());
    }

    @Override
    public WebServerRuntime runtime() {
        return runtime;
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
