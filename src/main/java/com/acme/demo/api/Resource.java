package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Resource.
 */
public class Resource extends ConfiguredTypeBase<ResourceConfig> {

    /**
     * Source of a {@link Resource}.
     */
    public enum Source {
        /**
         * Resource was loaded from a file.
         */
        FILE,
        /**
         * Resource was loaded from classpath.
         */
        CLASSPATH,
        /**
         * Resource was loaded from URL.
         */
        URL,
        /**
         * Resource was created with string content.
         */
        CONTENT,
        /**
         * Resource was created with binary content.
         */
        BINARY_CONTENT,
        /**
         * Resource was created with an input stream without knowledge of type.
         */
        UNKNOWN
    }

    private static final System.Logger LOGGER = System.getLogger(Resource.class.getName());

    private final Source source;
    private final String location;
    private final InputStream stream;

    private volatile boolean streamObtained;
    private volatile byte[] cachedBytes;

    protected Resource(ResourcePrototype prototype) {
        super(prototype);
        ResourceConfig config = prototype.config();
        if (config.resourcePath().isPresent()) {
            source = Source.CLASSPATH;
            location = config.resourcePath().orElseThrow();
            @SuppressWarnings("resource")
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
            stream = Objects.requireNonNull(is, "Resource path does not exist: " + location);
        } else if (config.uri().isPresent()) {
            source = Source.URL;
            URI uri = config.uri().orElseThrow();
            location = uri.toString();
            try {
                stream = uri.toURL().openStream();
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to open stream to uri: " + uri, e);
            }
        } else if (config.path().isPresent()) {
            source = Source.FILE;
            Path fsPath = config.path().orElseThrow();
            location = fsPath.toAbsolutePath().toString();
            try {
                stream = Files.newInputStream(fsPath);
            } catch (IOException e) {
                throw new UncheckedIOException("Resource on path: " + fsPath.toAbsolutePath() + " does not exist", e);
            }
        } else if (config.contentPlain().isPresent()) {
            source = Source.CONTENT;
            location = config.configKey() + ".content-plain";
            String content = config.contentPlain().orElseThrow();
            stream = new ByteArrayInputStream(content.getBytes(UTF_8));
        } else if (config.content().isPresent()) {
            source = Source.BINARY_CONTENT;
            location = config.configKey() + ".content-plain";
            String content = config.content().orElseThrow();
            byte[] bytes = Base64.getDecoder().decode(content);
            stream = new ByteArrayInputStream(bytes);
        } else {
            InputStream is = prototype.inputStream();
            if (is == null) {
                throw new IllegalArgumentException("Unable to create resource");
            }
            source = Source.UNKNOWN;
            stream = is;
            location = prototype.description();
        }
    }

    /**
     * Get an input stream to this resource.
     * If this method is called first, you actually get "THE" stream
     * to the resource and there will be no buffering done.
     * Once this happens, you cannot call any other method on this instance.
     * If you create the resource with byte content (e.g. from string), the content
     * will be pre-buffered.
     * <p>
     * If you first call another method (such as {@link #bytes()}, or explicitly buffer
     * this resource {@link #cacheBytes()}), you will get a new input stream to the
     * buffered bytes and may call this method multiple times.
     *
     * @return input stream ready to read bytes
     * @throws IllegalStateException in case the stream was already provided in previous call and was not buffered
     */
    public InputStream stream() {
        check();
        streamObtained = true;
        if (null == cachedBytes) {
            return stream;
        } else {
            return new ByteArrayInputStream(cachedBytes);
        }
    }

    /**
     * Get bytes of this resource.
     * Buffers the resource bytes in memory.
     *
     * @return bytes of this resource
     * @throws IllegalStateException in case the stream was already provided in previous call and was not buffered
     */
    public byte[] bytes() {
        check();
        cacheBytes();
        return cachedBytes;
    }

    /**
     * Get string content of this resource.
     * Buffers the resource bytes in memory.
     *
     * @return string content of this instance, using UTF-8 encoding to decode bytes
     * @throws IllegalStateException in case the stream was already provided in previous call and was not buffered
     */
    public String string() {
        check();
        cacheBytes();
        return new String(cachedBytes, UTF_8);
    }

    /**
     * Get string content of this resource.
     * Buffers the resource bytes in memory.
     *
     * @param charset Character set (encoding) to use to decode bytes
     * @return string content of this instance, using your encoding to decode bytes
     * @throws IllegalStateException in case the stream was already provided in previous call and was not buffered
     */
    public String string(Charset charset) {
        check();
        cacheBytes();
        return new String(cachedBytes, charset);
    }

    /**
     * Type of this resource, depends on the original source.
     *
     * @return type
     */
    public Source sourceType() {
        return source;
    }

    /**
     * Location (or description) of this resource, depends on original source.
     * Depending on source, this may be:
     * <ul>
     * <li>FILE - absolute path to the file</li>
     * <li>CLASSPATH - resource path</li>
     * <li>URL - string of the URI</li>
     * <li>CONTENT - either config key or provided description</li>
     * <li>BINARY_CONTENT - either config key or provided description </li>
     * <li>UNKNOWN - provided description</li>
     * </ul>
     *
     * @return location of this resource (or other description of where it comes from)
     */
    public String location() {
        return location;
    }

    /**
     * Caches the resource bytes in memory, so they can be repeatedly
     * accessed.
     * Be VERY careful with all methods that cache the bytes, as this may cause
     * a memory issue!
     */
    public void cacheBytes() {
        if (cachedBytes == null) {
            try {
                cachedBytes = stream.readAllBytes();
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to fully read resource bytes for resource: " + source + "("
                        + location + ")", e);
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    LOGGER.log(System.Logger.Level.WARNING,
                            "Failed to close input stream for resource: " + source + "(" + location + ")");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Resource { source='" + source + "',"
                + "location='" + location + "'}";
    }

    private void check() {
        if (streamObtained && (cachedBytes == null)) {
            throw new IllegalStateException(
                    "Once you get the stream, you cannot call other methods on this resource:" + source + " ("
                            + location + ")");
        }
    }
}
