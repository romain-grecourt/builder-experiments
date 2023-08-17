# Builder Experiments

* [Goals](#goals)
* [Builder Input](#builder-input)
* [Runtime Objects](#runtime-objects)
* [Config View](#config-view)
* [TypedConfig](#typed-config)
* [Implementation](#implementation)
* [Visibility](#visibility)

## Goals

The obvious advantages for code generating builders are:
- Ensuring consistent patterns across all builders
- Reducing code duplication to ease the maintenance (boilerplate == bugs)

However, the downfalls are:
- Forward references to generated code
  - can't be avoided, but can be minimized
- Obscure code generator "configuration"
  - complex codegen with annotations will always be somewhat obscure

## Builder Input

A builder represents the input to the final object, However, a builder is by definition mutable and thus isn't usable as a type
 to represent the input.

We can solve that by introducing a type called `Prototype` between the builder and the final object.
The `Prototype` is an immutable version of the builder that is valid outside the context of building.

```java
class Greeting {

    interface Prototype extends Builder.prototype {
        String message();
    }

    private final String message;

    Greeting(Prototype prototype) {
        this.message = prototype.message();
    }

    String sayHello() {
        return "Hello: " + message;
    }

    // forward reference to generated code
    static GreetingBuilder builder() {
        return new GreetingBuilder();
    }
}

// code-generated
class GreetingBuilder {

    private String message;

    GreetingBuilder message(String message) {
        this.message = message;
        return this;
    }

    Greeting build() {
        return new Greeting(new PrototypeImpl(message));
    }

    // code-generated
    static final class PrototypeImpl implements Greeting.Prototype {
        private final String message;

        PrototypeImpl(GreetingBuilder builder) {
            this.message = builder.message;
        }
        
        String message() {
            return message;
        }
    }
}
```

## Runtime Objects

An object that is built with a builder is a runtime object, it processes the prototype and exposes a "runtime" API.

E.g.

```java
class SocketListener {

    interface Prototype extends Builder.prototype {
        int port(); // defaults to 0
        String host(); // defaults to 0.0.0.0
    }

    private final int port;

    SocketListener(Prototype prototype) {
        var isa = new InetSocketAddress(prototype.address(), prototype.port);
        this.port = isa.getPort();
    }

    int port() {
        return port;
    }
}
```

In the example above, the value of the runtime object `.port()` differs from the value of `prototype.port()`.

## Config View

When building complex objects with nested builders, the prototypes must reference runtime objects.
Otherwise, it wouldn't be possible to re-use runtime objects instances.

```java
class Tls {

    interface Prototype extends Builder.prototype {
        String privateKey(); // resource path to a keystore
    }

    private final java.security.PrivateKey privateKey;

    Tls(Prototype prototype) {
        // use prototype.privateKey() to initialize this.privateKey
    }

    java.security.PrivateKey privateKey() {
        return privateKey;
    }
}

class SocketListener {

    interface Prototype extends Builder.prototype {
        Tls tls();
        int port();
        String host();
    }

    private final int port;
    private final Tls tls;

    SocketListener(Prototype prototype) {
        var isa = new InetSocketAddress(prototype.address(), prototype.port);
        this.port = isa.getPort();
        this.tls = prototype.tls();
    }

    int port() {
        return port;
    }

    Tls tls() {
        return tls;
    }
}

class Server {

    interface Prototype extends Builder.prototype {
        SocketListener listener();
    }

    Server(Prototype prototype) {
        SocketListener listener = prototype.listener();
    }
}
```

This models doesn't provide a way to expose a "config only" view.
I.e. a config view is an object with the same structure as the runtime object but that returns config values only.

```java
class Main {
    public static void main(String[] args) {
      System.out.print(server.prototype().listener().port()); // 1234 (effective port)
      System.out.print(server.prototype().listener().prototype().port()); // 0
      System.out.print(server.prototype().listener().tls().privateKey()); // java.security.privateKey@12345 (loaded private key)
      System.out.print(server.prototype().listener().prototype().tls().prototype().privateKey()); // server.p12 (configured private key)
    }
}
```

## Typed Config

`TypedConfig` is a "second" prototype with the purpose of being a dedicated config type that defines a strict config view.
 It can reference other `TypedConfig` but not runtime types.

`ConfiguredPrototype` is a variant of prototype that composes a `TypedConfig`. It can reference other runtime types but not
 `TypedConfig`. It also provides a way to define builder methods that don't have a config mapping.

```java
class Tls extends ConfiguredTypeBase<Tls.TypedConfig> {

    interface TypedConfig extends ConfigType {
        String privateKey();
    }

    interface Prototype extends ConfiguredPrototype<TypedConfig> {
        java.security.PrivateKey privateKey();
    }

    private final java.security.PrivateKey privateKey;

    Tls(Prototype prototype) {
        super(prototype);
        if (prototype.privateKey() != null) {
            this.privateKey = prototype.privateKey();
        } else {
            this.privateKey = loadPrivateKey(prototype.config().privateKey());
        }
    }

    java.security.PrivateKey privateKey() {
        return privateKey;
    }
}

class SocketListener extends ConfiguredTypeBase<Tls.TypedConfig> {

    interface TypedConfig extends ConfigType {
        Tls.TypedConfig tls();
        int port();
        String host();
    }

    interface Prototype extends ConfiguredPrototype<TypedConfig> {
        Tls tls();
    }

    private final int port;
    private final Tls tls;

    SocketListener(Prototype prototype) {
        super(prototype);
        var isa = new InetSocketAddress(prototype.address(), prototype.port);
        this.port = isa.getPort();
        this.tls = prototype.tls();
    }

    int port() {
        return port;
    }

    Tls tls() {
        return tls;
    }
}

class Server extends ConfiguredTypeBase<Tls.TypedConfig> {

    interface TypedConfig extends ConfigType {
        SocketListener.TypedConfig listener();
    }

    interface Prototype extends ConfiguredPrototype<TypedConfig> {
        SocketListener listener();
        MediaContext mediaContext(); // builder only
    }

    Server(Prototype prototype) {
        super(prototype);
        SocketListener listener = prototype.listener();
    }
}
```

```java
class Main {
    public static void main(String[] args) {
        System.out.print(server.prototype().listener().port()); // 1234 (effective port)
        System.out.print(server.typedConfig().listener().port()); // 0 (configured port)
        System.out.print(server.prototype().listener().tls().privateKey()); // java.security.privateKey@12345 (loaded private key)
        System.out.print(server.typedConfig().listener().tls().privateKey()); // server.p12 (configured private key)
    }
}
```

## Implementation

TODO describe the generated code needed to support `TypedConfig`.

## Visibility

TODO describe how exposes the minimum require to reduce the API surface.
