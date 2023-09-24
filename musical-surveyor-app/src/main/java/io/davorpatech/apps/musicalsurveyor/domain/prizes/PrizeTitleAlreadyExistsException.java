package io.davorpatech.apps.musicalsurveyor.domain.prizes;

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
 * Primary Key has been violated. In this case, the affected field is
 * the {@code title} of the {@code Prize} domain object.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class PrizeTitleAlreadyExistsException extends PreconditionalException // NOSONAR
        implements ErrorDomain, AdditionalArgumentsPopulator // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1253316887668366131L;

    private final String prizeTitle;

    /**
     * Construct a {@code PrizeTitleAlreadyExistsException} with the specified
     * arguments.
     *
     * @param prizeTitle the affected prize title
     */
    public PrizeTitleAlreadyExistsException(String prizeTitle) {
        super(String.format("The prize title `%s` already exist.", prizeTitle));
        this.prizeTitle = prizeTitle;
    }

    /**
     * Construct a {@code PrizeTitleAlreadyExistsException} with the specified
     * arguments.
     *
     * @param prizeTitle the affected prize title
     * @param cause      the cause. It can be {@code null}
     */
    public PrizeTitleAlreadyExistsException(String prizeTitle, Throwable cause) {
        this(prizeTitle);
        initCause(cause);
    }

    @Override
    public String getDomain() {
        return PrizeConstants.DOMAIN_NAME;
    }

    /**
     * Returns the affected field the domain object.
     *
     * <p>In this case, the affected field is the {@code email}.
     *
     * @return the affected field of the domain object
     */
    public String getFieldName() {
        return "title";
    }

    /**
     * Returns the rejected value of the affected field.
     *
     * <p>In this case, the rejected value is the {@code title}.
     *
     * @return the rejected value of the affected field
     */
    public String getFieldValue() {
        return prizeTitle;
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

