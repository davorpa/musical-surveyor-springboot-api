package io.davorpatech.fwk.web.servlet.error.attributes.customizers;

import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizerSupport;
import io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties;
import jakarta.servlet.RequestDispatcher;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * ErrorAttributesCustomizer} that always adds the current response status to
 * the processed error attributes map.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 *      ExtensibleErrorAttributes
 * @see HttpStatus
 */
public class StatusErrorAttributeAdder // NOSONAR
        extends ErrorAttributesCustomizerSupport // NOSONAR
        implements Ordered // NOSONAR
{
    /**
     * Constructs a new {@link StatusErrorAttributeAdder} with the given configuration
     * properties as arguments.
     *
     * @param customizerProperties the error attributes configuration properties
     */
    public StatusErrorAttributeAdder(
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
        final Map<String, Object> statusAttributes = new LinkedHashMap<>();
        addStatus(statusAttributes, webRequest);
        putInDeepPath(errorAttributes,
                customizerProperties.getStatus().getAttrPath(),
                statusAttributes);
    }

    @Override
    public int getOrder()
    {
        return customizerProperties.getStatus().getOrder();
    }

    /**
     * Adds the HTTP response status attributes to the {@code statusAttributes} map.
     *
     * @param statusAttributes  a map to be populated with the error attributes
     * @param requestAttributes the source request
     */
    protected void addStatus(
            final @NonNull Map<String, Object> statusAttributes,
            final @NonNull RequestAttributes requestAttributes)
    {
        Integer status = getAttribute(requestAttributes, RequestDispatcher.ERROR_STATUS_CODE);
        if (status == null) {
            statusAttributes.put("code", 999);
            statusAttributes.put("reason", "None");
            return;
        }

        statusAttributes.put("code", status);
        String reason;
        try {
            reason = HttpStatus.valueOf(status).getReasonPhrase();
        } catch (Exception ex) {
            // Unable to obtain a reason
            reason = "HTTP Status " + status;
        }
        statusAttributes.put("reason", reason);
    }
}
