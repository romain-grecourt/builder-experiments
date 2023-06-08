package com.acme.common;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Reads a PEM file and converts it into a list of DERs so that they are imported into a {@link java.security.KeyStore} easily.
 */
public final class PemReader {

    private PemReader() {
    }

    public static PublicKey readPublicKey(InputStream input) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static PrivateKey readPrivateKey(InputStream input, char[] password) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static List<X509Certificate> readCertificates(InputStream certStream) {
        throw new UnsupportedOperationException("Not implemented");
    }

}

