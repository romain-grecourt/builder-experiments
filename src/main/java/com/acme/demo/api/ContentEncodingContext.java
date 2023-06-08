package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBase;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.configurable.ServiceProviderPrototype;
import com.acme.demo.spi.ContentEncodingProvider;

/**
 * Content encoding support to obtain encoders and decoders.
 */
public class ContentEncodingContext extends ConfiguredTypeBase<ServiceProviderConfig<ContentEncodingProvider>> {

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    protected ContentEncodingContext(ServiceProviderPrototype<ContentEncodingProvider> prototype) {
        super(prototype);
    }
}
