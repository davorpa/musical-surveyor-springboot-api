package io.davorpatech.fwk.web.servlet.error.attributes;

import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides a base support for implementing any {@link ErrorAttributesCustomizer}.
 */
public abstract class ErrorAttributesCustomizerSupport // NOSONAR
        implements ErrorAttributesCustomizer // NOSONAR
{
    protected final ErrorAttributesCustomizerProperties customizerProperties;

    /**
     * Constructs a new {@link ErrorAttributesCustomizerSupport} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    protected ErrorAttributesCustomizerSupport(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        Assert.notNull(customizerProperties, "ErrorAttributesCustomizerProperties must not be null!");
        this.customizerProperties = customizerProperties;
    }

    /**
     * Return the underlying cause of the error or {@code null} if the error
     * cannot be extracted.
     *
     * @param webRequest the source request
     * @return the {@link Exception} that caused the error or {@code null}
     */
    @Nullable
    protected final Throwable getError(
            final @NonNull WebRequest webRequest)
    {
        Throwable exception = getAttribute(
                webRequest, ExtensibleErrorAttributes.ERROR_INTERNAL_ATTRIBUTE);
        if (exception == null) {
            exception = getAttribute(webRequest, RequestDispatcher.ERROR_EXCEPTION);
        }
        // store the exception in a well-known attribute to make it available to metrics
        // instrumentation.
        webRequest.setAttribute(
                ErrorAttributes.ERROR_ATTRIBUTE, exception, RequestAttributes.SCOPE_REQUEST);
        return exception;
    }

    /**
     * Unwraps the error cause of an exception, if needed, before threat it as
     * error attribute.
     *
     * @param error the error cause to unwrap
     * @return the unwrapped error
     * @see #getError(WebRequest)
     *
     */
    @Nullable
    protected final Throwable unwrapErrorCauseIfNeeded(
            @Nullable Throwable error)
    {
        if (error != null) {
            while (needsErrorCauseUnwrap(error) && error.getCause() != null) { // walk
                error = error.getCause();
            }
        }
        return error;
    }

    /**
     * Finds out if an exception needs cause unwrap before threat it as error
     * attribute.
     *
     * <p>By default, the test only matches for {@link ServletException}.
     *
     * @param error the error cause to test unwrapping
     * @return {@code true} if needed
     */
    protected boolean needsErrorCauseUnwrap(
            final Throwable error)
    {
        return error instanceof ServletException;
    }

    /**
     * Returns the message to be included as the value of the {@code message} error
     * attribute. By default, the returned message is the first of the following that
     * is not empty:
     * <ol>
     * <li>Value of the {@link RequestDispatcher#ERROR_MESSAGE} request attribute.
     * <li>Message of the given {@code error}.
     * <li>{@code No message available}.
     * </ol>
     * @param webRequest current request
     * @param error current error, if any
     * @return message to include in the error attributes
     */
    @NonNull
    protected String getMessage(
            final @NonNull WebRequest webRequest,
            final @Nullable Throwable error)
    {
        final Object message = getAttribute(webRequest, RequestDispatcher.ERROR_MESSAGE);
        if (!ObjectUtils.isEmpty(message)) {
            return message.toString();
        }
        if (error != null && StringUtils.hasLength(error.getMessage())) {
            return error.getMessage();
        }
        return "No message available";
    }

    /**
     * Retrieves an attribute from the request context given its name.
     *
     * @param <T> the component type of the attribute to retrieve
     * @param requestAttributes the source request
     * @param name              the name of the attribute to retrieve
     * @return the request attribute given its name, {@code null} if not exists
     */
    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(
            final @NonNull RequestAttributes requestAttributes,
            final @NonNull String name)
    {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Walks the path in depth to put the value of a property within the tree of
     * properties defined in the source map. If some intermediate property doesn't
     * exist, is set with an empty map.
     *
     * <p>A valid path is defined by one or more words separated with dots
     * (e.g. {@code "acme.system.feature"}).
     *
     * @param source the map source used as root, never {@code null}
     * @param path   a string representing the path where install a property value,
     *               never {@code null}
     * @param value  the value to set as last slug of the path
     * @return the node where the property
     * @throws IllegalStateException if some intermediate parts of the path not
     *                               represents a Map type
     */
    @NonNull
    protected Map<String, Object> putInDeepPath(
            @NonNull Map<String, Object> source,
            final @NonNull String path,
            final @Nullable Object value)
    {
        String[] keys = path.split("\\.");
        StringBuilder currentPath = new StringBuilder(path.length());
        for (int i = 0, iMax = keys.length - 1; ; i++) {
            currentPath.append(keys[i]);
            if (i == iMax) {
                source.put(keys[i], value);
                return source;
            }
            // deep walk initializing dynamic node if absent
            Object node = source.computeIfAbsent(keys[i], key -> new LinkedHashMap<>());
            if (node instanceof Map) {
                source = (Map<String, Object>) node;
                currentPath.append('.');
                continue;
            }
            throw new IllegalStateException(String.format(
                    "Property '%s' must be a Map but is a '%s'!",
                    currentPath, node.getClass().getName()));
        }
    }
}
