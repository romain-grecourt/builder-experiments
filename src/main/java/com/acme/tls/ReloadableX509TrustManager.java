package com.acme.tls;

import com.acme.demo.api.Tls;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;

public class ReloadableX509TrustManager implements X509TrustManager, TlsReloadableComponent {

    private static final System.Logger LOGGER = System.getLogger(ReloadableX509TrustManager.class.getName());

    private volatile X509TrustManager trustManager;

    public ReloadableX509TrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        trustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        trustManager.checkServerTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return trustManager.getAcceptedIssuers();
    }

    @Override
    public void reload(Tls tls) {
        Objects.requireNonNull(tls.originalKeyManager(), "Cannot unset trust store");
        if (LOGGER.isLoggable(System.Logger.Level.DEBUG)) {
            LOGGER.log(System.Logger.Level.DEBUG, "Reloading TLS X509TrustManager");
        }
        this.trustManager = tls.originalTrustManager();
    }

}

