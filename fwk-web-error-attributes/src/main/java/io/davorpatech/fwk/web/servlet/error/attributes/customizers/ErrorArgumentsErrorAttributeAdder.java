package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
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
 * @see io.davorpatech.fwk.model.AdditionalArgumentsPopulator
 *      AdditionalArgumentsPopulator
 */
public class ErrorArgumentsErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    protected final Environment environment;

    /**
     * Constructs a new {@link ErrorArgumentsErrorAttributeAdder} with the given
     * arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     * @param environment          the application environment
     */
    public ErrorArgumentsErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties,
            final @NonNull Environment environment)
    {
        super(customizerProperties);
        Assert.notNull(environment, "Parameter 'environment' must not be null!");
        this.environment = environment;
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

        if (!(error instanceof AdditionalArgumentsPopulator)) return;
        final AdditionalArgumentsPopulator populator = (AdditionalArgumentsPopulator) error;

        final Map<String, Object> arguments = new LinkedHashMap<>();
        populator.populate(environment, arguments);

        putInDeepPath(errorAttributes,
                customizerProperties.getArgsPopulate().getAttrPath(),
                arguments);
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getArgsPopulate().getOrder();
    }
}
