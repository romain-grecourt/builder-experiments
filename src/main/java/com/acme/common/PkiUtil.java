package com.acme.common;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Utilities to handle PKI keystores, certificates and keys.
 */
public final class PkiUtil {

    private PkiUtil() {
    }

    public static KeyStore loadKeystore(String keystoreType, InputStream storeStream, char[] keystorePassphrase, String message) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static PrivateKey loadPrivateKey(KeyStore keyStore, String keyAlias, char[] keyPassphrase) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static List<X509Certificate> loadCertChain(KeyStore keyStore, String certAlias) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static X509Certificate loadCertificate(KeyStore keyStore, String certAlias) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static List<X509Certificate> loadCertificates(KeyStore keyStore) {
        throw new UnsupportedOperationException("Not implemented");
    }
}

