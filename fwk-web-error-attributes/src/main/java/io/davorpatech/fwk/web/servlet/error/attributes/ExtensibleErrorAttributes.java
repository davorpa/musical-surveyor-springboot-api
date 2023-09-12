package io.davorpatech.fwk.web.servlet.error.attributes;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ErrorAttributes} that delegates the error attributes
 * building/composition into a collection of customizers.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExtensibleErrorAttributes  // NOSONAR
        implements ErrorAttributes, HandlerExceptionResolver, Ordered // NOSONAR
{
    static final String ERROR_INTERNAL_ATTRIBUTE = ExtensibleErrorAttributes.class.getName() + ".ERROR";

    protected final List<ErrorAttributesCustomizer> errorAttributesCustomizers;

    /**
     * Constructs a new {@link ExtensibleErrorAttributes} using the given customizers.
     *
     * @param errorAttributesCustomizers the customizers used to build the error
     *                                   attributes, never {@code null}
     */
    public ExtensibleErrorAttributes(
            final @NonNull List<ErrorAttributesCustomizer> errorAttributesCustomizers)
    {
        Assert.notNull(errorAttributesCustomizers, "ErrorAttributesCustomizers must not be null!");
        this.errorAttributesCustomizers = errorAttributesCustomizers;
    }

    @Override
    public int getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(
            final HttpServletRequest request, final HttpServletResponse response,
            final Object handler,
            final Exception ex)
    {
        storeErrorAttributes(request, ex);
        return null;
    }

    private void storeErrorAttributes(
            final HttpServletRequest request,
            final Exception ex)
    {
        request.setAttribute(ERROR_INTERNAL_ATTRIBUTE, ex);
    }

    @Override
    public Map<String, Object> getErrorAttributes(
            final @NonNull WebRequest webRequest,
            final @NonNull ErrorAttributeOptions options)
    {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        for (ErrorAttributesCustomizer customizer : this.errorAttributesCustomizers) {
            customizer.customize(errorAttributes, webRequest, options);
        }
        return  errorAttributes;
    }

    @Override
    public Throwable getError(
            @NonNull WebRequest webRequest)
    {
        Throwable exception = getAttribute(webRequest, ERROR_INTERNAL_ATTRIBUTE);
        if (exception == null) {
            exception = getAttribute(webRequest, RequestDispatcher.ERROR_EXCEPTION);
        }
        // store the exception in a well-known attribute to make it available to metrics
        // instrumentation.
        webRequest.setAttribute( // NOSONAR
                ErrorAttributes.ERROR_ATTRIBUTE, exception, RequestAttributes.SCOPE_REQUEST);
        return exception;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(
            final @NonNull RequestAttributes requestAttributes,
            final @NonNull String name)
    {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
