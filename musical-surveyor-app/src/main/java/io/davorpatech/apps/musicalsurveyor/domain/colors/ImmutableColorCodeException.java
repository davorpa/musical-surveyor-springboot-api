package io.davorpatech.apps.musicalsurveyor.domain.colors;

import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Exception that is thrown when we try to change color codes to other
 * different value.
 *
 * Normally, color codes are immutable, but can change if they are not
 * used by any raffle ticket.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ImmutableColorCodeException extends PreconditionalException // NOSONAR
    implements Identifiable<Serializable>, ErrorDomain, AdditionalArgumentsPopulator // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -5459554575281999370L;

    private final Serializable id;

    private final String currentValue;

    private final String rejectedValue;

    /**
     * Construct a {@code ImmutableColorCodeException} with the specified arguments.
     *
     * @param id            the color ID
     * @param currentValue  the current value of the color code
     * @param rejectedValue the rejected value of the color code
     */
    public ImmutableColorCodeException(
            Serializable id, String currentValue, String rejectedValue) {
        super(String.format(
            "Unable change the code of color %s from `%s` to `%s`. Color codes are immutable.",
            id, currentValue, rejectedValue));
        this.id = id;
        this.currentValue = currentValue;
        this.rejectedValue = rejectedValue;
    }

    @Override
    public String getDomain() {
        return ColorConstants.DOMAIN_NAME;
    }

    /**
     * Returns the color ID where the business rule has been violated.
     *
     * @return the identifier of the color
     */
    @Override
    public Serializable getId() {
        return id;
    }

    /**
     * Returns the affected field of the domain object.
     *
     * <p>In this case, the affected field is the {@code color code}.
     *
     * @return the affected field of the domain object
     */
    public String getFieldName() {
        return "code";
    }

    /**
     * Returns the current value of the color code.
     *
     * @return the current value of the color code
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * Returns the rejected value of the color code.
     *
     * @return the rejected value of the color code
     */
    public String getRejectedValue() {
        return rejectedValue;
    }

    @Override
    public void populate(
            final Environment environment,
            final Map<String, Object> attributes)
    {
        attributes.put("field", getFieldName());
        attributes.put("currentValue", getCurrentValue());
        attributes.put("rejectedValue", getRejectedValue());
    }
}
