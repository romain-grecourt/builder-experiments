package com.acme.tls;

/**
 * Type of client authentication.
 */
public enum TlsClientAuth {
    /**
     * Mutual TLS is required.
     * Server MUST present a certificate trusted by the client, client MUST present a certificate trusted by the server.
     * This implies private key and trust configuration for both server and client.
     */
    REQUIRED,
    /**
     * Mutual TLS is optional.
     * Server MUST present a certificate trusted by the client, client MAY present a certificate trusted by the server.
     * This implies private key configuration at least for server, trust configuration for at least client.
     */
    OPTIONAL,
    /**
     * Mutual TLS is disabled.
     * Server MUST present a certificate trusted by the client, client does not present a certificate.
     * This implies private key configuration for server, trust configuration for client.
     */
    NONE
}
