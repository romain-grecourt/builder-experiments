package com.acme.demo.app;

import com.acme.demo.api.SocketListener;
import com.acme.demo.api.SocketListenerFactory;
import com.acme.demo.api.WebServer;
import com.acme.demo.api.WebServerBuilder;
import com.acme.demo.api.WebServerFactory;
import io.helidon.config.Config;

public class Main {

    public static void main(String[] args) {
        WebServer server = WebServer.builder()
//                                    .socket("@admin", SocketListener.builder().host("127.0.0.1").port(8081))
                                    .socket("@default", socket -> socket.host("localhost").port(8080))
                                    .build()
                                    .start();

        SocketListener socketListener = server.sockets().get("@default");
        int effectivePort = socketListener.port();

        int configuredPort1 = server.config()
                                    .sockets()
                                    .get("@default")
                                    .port();

        SocketListener.TypedConfig config = socketListener.config();
        int configuredPort2 = config.port();
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
        SocketListener.TypedConfig socketListenerConfig = SocketListenerFactory.typedConfig(); // default values
        SocketListenerFactory.create(socketListenerConfig);

        Config config = Config.create();
        WebServer.TypedConfig serverConfig = WebServerFactory.typedConfig(config.get("server"));
        WebServer server = WebServerFactory.create(serverConfig);
        server.start();
    }

    public static void main4(String[] args) {
        Config config = Config.create();
        WebServer server = WebServer.builder()
                                    .configure(config.get("server"))
                                    .start();
    }

    public static void main5(String[] args) {
        WebServerBuilder builder = WebServer.builder();
        WebServer server = builder.build();
        WebServer.TypedConfig serverConfig = server.config();
        server.sockets().get("@default").port(); // 1234
        serverConfig.sockets().get("@default").port(); // 0
    }

    public static void main6(String[] args) {
        Config config = Config.create();
        WebServer.TypedConfig serverConfig = WebServerFactory.typedConfig(config.get("server"));
        WebServer server = WebServerFactory.create(serverConfig);
    }
}
