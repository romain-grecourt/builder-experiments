package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBase;
import com.acme.configurable.ServiceProviderConfig;
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
    protected MediaContext(MediaContextPrototype prototype) {
        super(prototype);
    }
}
