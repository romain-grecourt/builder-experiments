package com.acme.demo.api;

import com.acme.builder.Factory;

/**
 * {@link WebServer} factory.
 * This class is code generated.
 */
public class WebServerFactory extends Factory<WebServer> {

    WebServerFactory(WebServerBuilder builder) {
        super(() -> WebServer.create(builder));
    }
}
