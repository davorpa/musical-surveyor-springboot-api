package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.model.ErrorDomain;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
public class EmailAlreadyExistException extends PreconditionalException // NOSONAR
        implements ErrorDomain, AdditionalArgumentsPopulator // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 6769828224152068983L;

    private final String email;

    /**
     * Construct a {@code EmailAlreadyExistException} with the specified
     * arguments.
     *
     * @param email the affected email
     */
    public EmailAlreadyExistException(String email) {
        super(String.format("The email `%s` already exist.", email));
        this.email = email;
    }

    /**
     * Construct a {@code EmailAlreadyExistException} with the specified
     * arguments.
     *
     * @param email the affected email
     * @param cause the cause. It can be {@code null}
     */
    public EmailAlreadyExistException(String email, Throwable cause) {
        this(email);
        initCause(cause);
    }

    @Override
    public String getDomain() {
        return RadioListenerConstants.DOMAIN_NAME;
    }

    /**
     * Returns the affected field the domain object.
     *
     * <p>In this case, the affected field is the {@code email}.
     *
     * @return the affected field of the domain object
     */
    public String getFieldName() {
        return "email";
    }

    /**
     * Returns the rejected value of the affected field.
     *
     * <p>In this case, the rejected value is the {@code email}.
     *
     * @return the rejected value of the affected field
     */
    public String getFieldValue() {
        return email;
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

