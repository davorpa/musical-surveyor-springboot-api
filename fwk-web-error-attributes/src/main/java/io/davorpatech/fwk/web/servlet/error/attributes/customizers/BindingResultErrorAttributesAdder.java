package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that conditionally adds the binding errors to the
 * processed error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see ErrorAttributeOptions.Include#BINDING_ERRORS
 * @see BindingResult
 */
public class BindingResultErrorAttributesAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link BindingResultErrorAttributesAdder} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public BindingResultErrorAttributesAdder(
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
        error = unwrapErrorCauseIfNeeded(error);

        if (!(error instanceof BindingResult)) return;
        final BindingResult result = (BindingResult) error;

        if (options.isIncluded(ErrorAttributeOptions.Include.MESSAGE)) {
            putInDeepPath(errorAttributes,
                    customizerProperties.getErrorMessage().getAttrPath(),
                    resolveBindingErrorMessage(webRequest, result));
        }
        if (options.isIncluded(ErrorAttributeOptions.Include.BINDING_ERRORS)) {
            putInDeepPath(errorAttributes,
                    customizerProperties.getBindingErrors().getAttrPath(),
                    resolveBindingErrors(webRequest, result));
        }
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getBindingErrors().getOrder();
    }

    /**
     * Resolves a descriptive error message for the given binding result.
     *
     * @param webRequest the source request
     * @param result     the binding result
     * @return a descriptive error message for the given binding result
     */
    protected String resolveBindingErrorMessage(
            final @NonNull WebRequest webRequest,
            final @NonNull BindingResult result)
    {
        return String.format("Validation failed for object='%s'. Error count: %d",
                result.getObjectName(), result.getErrorCount());
    }

    /**
     * Resolves the errors source for the given binding result.
     *
     * @param webRequest the source request
     * @param result     the binding result
     * @return the binding result errors
     */
    protected List<? extends ObjectError> resolveBindingErrors( // NOSONAR
            final @NonNull WebRequest webRequest,
            final @NonNull BindingResult result)
    {
        return result.getAllErrors();
    }
}
