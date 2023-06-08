package com.acme.demo.api;

import com.acme.configurable.ServiceProviderPrototype;
import com.acme.demo.spi.MediaSupportProvider;

/**
 * Prototype for {@link MediaContext}.
 */
public interface MediaContextPrototype extends ServiceProviderPrototype<MediaSupportProvider> {

    /**
     * Configure an existing context as a fallback for this context
     *
     * @return {@link MediaContext}
     */
    MediaContext fallback();
}
