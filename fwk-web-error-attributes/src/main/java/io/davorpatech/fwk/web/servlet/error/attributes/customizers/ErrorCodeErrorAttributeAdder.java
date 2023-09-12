package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.model.ErrorCode;
import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that adds the additional arguments to the processed
 * error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see io.davorpatech.fwk.model.ErrorCode
 *      ErrorCode
 */
public class ErrorCodeErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link ErrorCodeErrorAttributeAdder} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public ErrorCodeErrorAttributeAdder(
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

        if (!(error instanceof ErrorCode)) return;
        final ErrorCode coder = (ErrorCode) error;

        putInDeepPath(errorAttributes,
                customizerProperties.getErrorCode().getAttrPath(),
                coder.getErrorCode());
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getErrorCode().getOrder();
    }
}
