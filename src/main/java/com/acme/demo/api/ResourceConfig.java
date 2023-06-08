package com.acme.demo.api;

import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

public interface ResourceConfig extends ProxyConfig {

    Optional<Path> path();

    Optional<String> resourcePath();

    Optional<URI> uri();

    Optional<String> contentPlain();

    Optional<String> content();
}
