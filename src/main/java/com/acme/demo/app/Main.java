package com.acme.demo.app;

import com.acme.demo.api.SocketListener;
import com.acme.demo.api.WebServer;
import com.acme.demo.api.WebServerConfig;

public class Main {

    public static void main(String[] args) {
        WebServer server = WebServer.builder()
                                    .socket("@default", socket -> socket.host("localhost").port(8080))
                                    .build()
                                    .start();

        int effectivePort = server.runtime()
                                  .sockets()
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
                                    .start();
    }


    public static void main3(String[] args) {

        WebServerConfig serverConfig = null; // config driven

        WebServer.create(serverConfig);
    }
}
