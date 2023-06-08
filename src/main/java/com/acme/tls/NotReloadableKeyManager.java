package com.acme.tls;

import com.acme.demo.api.Tls;

public class NotReloadableKeyManager implements TlsReloadableComponent {
    @Override
    public void reload(Tls tls) {
        if (tls.originalKeyManager() != null) {
            throw new UnsupportedOperationException("Cannot reload key manager if one was not set during server start");
        }
    }
}
