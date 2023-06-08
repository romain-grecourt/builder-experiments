package com.acme.tls;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;
import java.io.Serial;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;

import static java.lang.System.Logger.Level.TRACE;

public class TrustAllManagerFactory extends TrustManagerFactory {
    private static final TrustAllManagerFactorySpi SPI = new TrustAllManagerFactorySpi();
    private static final Provider PROVIDER = new Provider("helidon",
            "0.0",
            "Helidon internal security provider") {
        @Serial
        private static final long serialVersionUID = -147888L;
    };

    /**
     * Create a new instance.
     */
    public TrustAllManagerFactory() {
        super(SPI, PROVIDER, "insecure-trust-all");
    }

    private static final class TrustAllManagerFactorySpi extends TrustManagerFactorySpi {
        private final TrustManager[] managers = new TrustManager[] {new TrustAllManager()};

        private TrustAllManagerFactorySpi() {
        }

        @Override
        protected void engineInit(KeyStore keyStore) {
        }

        @Override
        protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        }

        @Override
        protected TrustManager[] engineGetTrustManagers() {
            return managers;
        }
    }

    private static class TrustAllManager implements X509TrustManager {
        private static final System.Logger LOGGER = System.getLogger(TrustAllManager.class.getName());
        private static final X509Certificate[] ACCEPTED_ISSUERS = new X509Certificate[0];

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            if (LOGGER.isLoggable(TRACE)) {
                LOGGER.log(TRACE, "Accepting a client certificate: " + chain[0].getSubjectX500Principal().getName()
                        + ", type: " + authType);
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            if (LOGGER.isLoggable(TRACE)) {
                LOGGER.log(TRACE, "Accepting a server certificate: " + chain[0].getSubjectX500Principal().getName()
                        + ", type: " + authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return ACCEPTED_ISSUERS;
        }
    }
}
