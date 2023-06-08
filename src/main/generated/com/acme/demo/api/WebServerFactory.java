package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeFactory;
import io.helidon.config.Config;

import java.util.HashMap;

/**
 * {@link WebServer} factory.
 */
public class WebServerFactory extends WebServerPrototypeBase
        implements ConfiguredTypeFactory<WebServerConfig, WebServer> {

    /**
     * Create a new instance.
     *
     * @param builder builder
     */
    WebServerFactory(WebServerBuilder builder) {
        super(new WebServerConfigImpl(builder), new HashMap<>(builder.sockets));
    }

    @Override
    public WebServer build() {
        return new WebServer(this);
    }

    /**
     * Create a new instance.
     *
     * @param config typed config
     * @return new instance
     */
    public static WebServer create(WebServerConfig config) {
        return new WebServer(new WebServerConfigPrototype(config));
    }

    /**
     * Create a new instance.
     *
     * @return new instance
     */
    public static WebServer create() {
        return create(WebServerConfigImpl.create());
    }

    /**
     * Create a new instance.
     *
     * @param config config node
     * @return new instance
     */
    public static WebServer create(Config config) {
        return create(WebServerConfigImpl.create(config));
    }
}
