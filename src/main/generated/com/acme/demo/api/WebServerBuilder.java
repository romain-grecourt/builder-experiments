package com.acme.demo.api;

import com.acme.builder.Builder;

/**
 * {@link WebServer} meta-builder.
 */
public class WebServerBuilder extends WebServerMetaBuilderBase<WebServerBuilder, WebServer> {

    WebServerBuilder() {
    }

    @Override
    protected Builder<WebServer> doBuild0() {
        return () -> new WebServer(WebServerFactory.prototype(this));
    }
}
