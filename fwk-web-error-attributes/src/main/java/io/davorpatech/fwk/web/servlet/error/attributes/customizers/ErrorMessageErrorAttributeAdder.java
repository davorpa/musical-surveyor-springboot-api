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
 * ErrorAttributesCustomizer} that conditionally adds the exception/error message
 * to the processed error attributes map.
 *
 * <p>The returned message is the first of the following that is not empty:
 * <ol>
 * <li>Value of the {@link jakarta.servlet.RequestDispatcher#ERROR_MESSAGE} request attribute.
 * <li>Message of the {@link #getError(WebRequest) attached exception}.
 * <li>{@code No message available}.
 * </ol>
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see ErrorAttributeOptions.Include#MESSAGE
 */
public class ErrorMessageErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link ErrorMessageErrorAttributeAdder} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public ErrorMessageErrorAttributeAdder(
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
        if (options.isIncluded(ErrorAttributeOptions.Include.MESSAGE)) {
            error = unwrapErrorCauseIfNeeded(error);

            putInDeepPath(errorAttributes,
                    customizerProperties.getErrorMessage().getAttrPath(),
                    getMessage(webRequest, error));
        }
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getErrorMessage().getOrder();
    }
}
