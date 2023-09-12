package io.davorpatech.fwk.web.servlet.error.attributes;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Each {@link ErrorAttributesCustomizer} can be used as sources for the
 * error attributes composition process.
 *
 * <p>There are no limitations on what each customizer can do. Most of them
 * will just stick to adding new attributes; but you can also modify those
 * already added previously, for example to reorganize them into subgroups
 * or remove them at the end depending on certain environment conditions.
 *
 * @see ExtensibleErrorAttributes
 * @see org.springframework.boot.web.servlet.error.ErrorAttributes
 */
public interface ErrorAttributesCustomizer
{
    /**
     * Customize a {@link Map} that acts an
     * {@link org.springframework.boot.web.servlet.error.ErrorAttributes
     * error attributes} source.
     *
     * <p>It can be used to populate new attributes or even modify those
     * already present.
     *
     * @param errorAttributes a map to be populated with the error attributes
     * @param webRequest      the source request
     * @param options         options for error attribute contents
     */
    void customize(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull WebRequest webRequest,
            final @NonNull ErrorAttributeOptions options);
}
