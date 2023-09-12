package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that conditionally adds the exception class name to
 * the processed error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see ErrorAttributeOptions.Include#EXCEPTION
 */
public class ExceptionTypeErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link ExceptionTypeErrorAttributeAdder} with the given
     * configuration properties as arguments.
     *
     * @param properties the error attributes configuration properties
     */
    public ExceptionTypeErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties properties)
    {
        super(properties);
    }

    @Override
    public void customize(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull WebRequest webRequest,
            final @NonNull ErrorAttributeOptions options)
    {
        Throwable error = getError(webRequest);
        if (error == null) return;
        if (options.isIncluded(ErrorAttributeOptions.Include.EXCEPTION)) {
            error = unwrapErrorCauseIfNeeded(error);

            putInDeepPath(errorAttributes,
                    customizerProperties.getExceptionType().getAttrPath(),
                    resolveExceptionName(error)); // NOSONAR
        }
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getExceptionType().getOrder();
    }

    /**
     * Transforms any exception type to its textual representation.
     *
     * <p>By default, the class name is returned.
     *
     * @param error the {@link Exception} that caused the error
     * @return the textual representation of the exception type
     */
    protected String resolveExceptionName(
            final @NonNull Throwable error)
    {
        return error.getClass().getName();
    }
}
