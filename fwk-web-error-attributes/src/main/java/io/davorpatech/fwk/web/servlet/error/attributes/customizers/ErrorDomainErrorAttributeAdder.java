package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that adds the failing domain entity type to the
 * processed error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see io.davorpatech.fwk.model.ErrorDomain
 *      ErrorDomain
 */
public class ErrorDomainErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link ErrorDomainErrorAttributeAdder} with the given
     * configuration properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public ErrorDomainErrorAttributeAdder(
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

        if (!(error instanceof ErrorDomain)) return;
        final ErrorDomain domain = (ErrorDomain) error;

        putInDeepPath(errorAttributes,
                customizerProperties.getErrorDomain().getAttrPath(),
                domain.getDomain());
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getErrorDomain().getOrder();
    }
}
