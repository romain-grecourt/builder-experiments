package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBase;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.configurable.ServiceProviderPrototype;
import com.acme.demo.spi.MediaSupportProvider;

/**
 * Media context.
 */
public class MediaContext extends ConfiguredTypeBase<ServiceProviderConfig<MediaSupportProvider>> {

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    protected MediaContext(Prototype prototype) {
        super(prototype);
    }

    /**
     * Prototype for {@link MediaContext}.
     */
    public interface Prototype extends ServiceProviderPrototype<MediaSupportProvider> {

        /**
         * Configure an existing context as a fallback for this context
         *
         * @return {@link MediaContext}
         */
        MediaContext fallback();
    }
}
