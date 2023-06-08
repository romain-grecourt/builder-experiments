package com.acme.tls;

import com.acme.demo.api.Tls;

public class NotReloadableTrustManager implements TlsReloadableComponent {
    @Override
    public void reload(Tls tls) {
        if (tls.originalTrustManager() != null) {
            throw new UnsupportedOperationException("Cannot set trust manager if one was not set during server start");
        }
    }
}
