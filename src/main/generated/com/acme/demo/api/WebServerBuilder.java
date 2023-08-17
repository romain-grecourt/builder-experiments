package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBuilder;

/**
 * {@link WebServer} builder.
 */
public class WebServerBuilder
        extends WebServerBuilderBase<WebServerBuilder>
        implements ConfiguredTypeBuilder<WebServerBuilder, WebServer> {

    WebServerBuilder() {
        // package-private to force the use of WebServerFactory
    }

    /**
     * Shorthand for {@code build().start()}.
     *
     * @return WebServer
     * @see WebServer#start()
     */
    public WebServer start() {
        return build().start();
    }

    @Override
    public WebServer build() {
        resolve();
        return WebServerFactory.create(this);
    }
}
