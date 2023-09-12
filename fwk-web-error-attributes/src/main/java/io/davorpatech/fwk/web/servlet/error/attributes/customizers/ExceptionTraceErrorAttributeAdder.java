package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that conditionally adds the exception stack trace to
 * the processed error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see ErrorAttributeOptions.Include#STACK_TRACE
 */
public class ExceptionTraceErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link ExceptionTraceErrorAttributeAdder} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public ExceptionTraceErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        super(customizerProperties);
    }

    @Override
    public void customize(
            final @NonNull Map<String, Object> errorAttributes,
            final @NonNull WebRequest webRequest,
            final @NonNull ErrorAttributeOptions options)
    {
        Throwable error = getError(webRequest);
        if (error == null) return;
        if (options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
            error = unwrapErrorCauseIfNeeded(error);

            putInDeepPath(errorAttributes,
                    customizerProperties.getStacktrace().getAttrPath(),
                    resolveStackTrace(error)); // NOSONAR
        }
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getStacktrace().getOrder();
    }

    /**
     * Transform any stacktrace of given exception to a textual representation.
     *
     * @param error the {@link Exception} that caused the error
     * @return the stack trace as String
     */
    protected String resolveStackTrace(
            final @NonNull Throwable error)
    {
        final StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        return stackTrace.toString();
    }
}
