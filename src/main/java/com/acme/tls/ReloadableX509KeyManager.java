package com.acme.tls;

import com.acme.demo.api.Tls;

import javax.net.ssl.X509KeyManager;
import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Objects;

public class ReloadableX509KeyManager implements X509KeyManager, TlsReloadableComponent {

    private static final System.Logger LOGGER = System.getLogger(ReloadableX509KeyManager.class.getName());

    private volatile X509KeyManager keyManager;

    public ReloadableX509KeyManager(X509KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @Override
    public String[] getClientAliases(String s, Principal[] principals) {
        return keyManager.getClientAliases(s, principals);
    }

    @Override
    public String chooseClientAlias(String[] strings, Principal[] principals, Socket socket) {
        return keyManager.chooseClientAlias(strings, principals, socket);
    }

    @Override
    public String[] getServerAliases(String s, Principal[] principals) {
        return keyManager.getServerAliases(s, principals);
    }

    @Override
    public String chooseServerAlias(String s, Principal[] principals, Socket socket) {
        return keyManager.chooseServerAlias(s, principals, socket);
    }

    @Override
    public X509Certificate[] getCertificateChain(String s) {
        return keyManager.getCertificateChain(s);
    }

    @Override
    public PrivateKey getPrivateKey(String s) {
        return keyManager.getPrivateKey(s);
    }

    @Override
    public void reload(Tls tls) {
        Objects.requireNonNull(tls.originalKeyManager(), "Cannot unset key manager");
        if (LOGGER.isLoggable(System.Logger.Level.DEBUG)) {
            LOGGER.log(System.Logger.Level.DEBUG, "Reloading TLS X509KeyManager");
        }
        this.keyManager = tls.originalKeyManager();
    }

}

