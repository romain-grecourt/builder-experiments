package com.acme.demo.app;

import com.acme.demo.api.SocketListener;
import com.acme.demo.api.SocketListenerFactory;
import com.acme.demo.api.WebServer;
import com.acme.demo.api.WebServerFactory;
import io.helidon.config.Config;

public class Main {

    public static void main(String[] args) {
        WebServer server = WebServer.builder()
//                                    .socket("@admin", SocketListener.builder().host("127.0.0.1").port(8081))
                                    .socket("@default", socket -> socket.host("localhost").port(8080))
                                    .build()
                                    .start();

        int effectivePort = server.sockets()
                                  .get("@default")
                                  .port();

        int configuredPort = server.config()
                                   .sockets()
                                   .get("@default")
                                   .port();
    }

    public static void main2(String[] args) {
        SocketListener socketListener = SocketListener.builder()
                                                      .host("localhost")
                                                      .port(8080)
                                                      .build();
        WebServer server = WebServer.builder()
                                    .socket("@default", socketListener)
                                    .build()
                                    .start();
    }

    public static void main3(String[] args) {

        SocketListener.TypedConfig socketListenerConfig = SocketListenerFactory.typedConfig();
        SocketListenerFactory.create(socketListenerConfig);


        Config config = Config.create();
        WebServer.TypedConfig serverConfig = WebServerFactory.typedConfig(config.get("server"));
        WebServer server = WebServerFactory.create(serverConfig);
        server.start();
    }
}
