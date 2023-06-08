package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

import java.io.InputStream;

public interface ResourcePrototype extends ConfiguredPrototype<ResourceConfig> {

    /**
     * Explicit description of the resource.
     *
     * @return resource description
     */
    String description();

    /**
     * Input stream.
     *
     * @return {@link InputStream}
     */
    InputStream inputStream();
}
