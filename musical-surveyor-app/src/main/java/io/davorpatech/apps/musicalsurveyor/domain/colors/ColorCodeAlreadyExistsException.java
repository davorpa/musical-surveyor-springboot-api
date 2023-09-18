package io.davorpatech.apps.musicalsurveyor.domain.colors;

import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.model.ErrorDomain;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Map;

/**
 * Exception that is thrown when the detail of a specific model object
 * (entity, POJO, DTO...) already exists when searching for any of its
 * identifiers.
 *
 * <p>This will normally be used to indicate that a database Unique or
 * Primary Key has been violated.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ColorCodeAlreadyExistsException extends PreconditionalException // NOSONAR
        implements ErrorDomain, AdditionalArgumentsPopulator // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 6888990351803757846L;

    private final String colorCode;

    /**
     * Construct a {@code ColorCodeAlreadyExistsException} with the specified
     * arguments.
     *
     * @param colorCode the affected color code
     */
    public ColorCodeAlreadyExistsException(String colorCode) {
        super(String.format("The color with code `%s` already exist.", colorCode));
        this.colorCode = colorCode;
    }

    /**
     * Construct a {@code ColorCodeAlreadyExistsException} with the specified
     * arguments.
     *
     * @param colorCode the affected color code
     * @param cause     the cause. It can be {@code null}
     */
    public ColorCodeAlreadyExistsException(String colorCode, @Nullable Throwable cause) {
        this(colorCode);
        initCause(cause);
    }

    @Override
    public String getDomain() {
        return ColorConstants.DOMAIN_NAME;
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
     * Returns the rejected value of the affected field.
     *
     * <p>In this case, the rejected value is the {@code color code}.
     *
     * @return the rejected value of the affected field
     */
    public String getFieldValue() {
        return colorCode;
    }

    @Override
    public void populate(
        final Environment environment,
        final Map<String, Object> attributes)
    {
        attributes.put("field", getFieldName());
        attributes.put("rejectedValue", getFieldValue());
    }
}
