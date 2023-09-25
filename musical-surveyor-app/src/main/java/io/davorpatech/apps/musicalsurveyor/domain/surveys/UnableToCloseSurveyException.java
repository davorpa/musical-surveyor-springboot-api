package io.davorpatech.apps.musicalsurveyor.domain.surveys;


import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * Exception raised when we try to close a survey but it has an internal state that
 * prevents it from being closed.
 *
 * The survey ID and the reason why the prize is locked are provided.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class UnableToCloseSurveyException extends PreconditionalException // NOSONAR
    implements Identifiable<Serializable>, ErrorDomain // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 4299456951564569984L;

    private final Serializable id;

    /**
     * Construct a {@code UnableToCloseSurveyException} with the specified arguments.
     *
     * @param id     the survey ID we tried to edit
     * @param reason the reason why cannot be closed
     */
    public UnableToCloseSurveyException(Serializable id, String reason)
    {
        super(String.format("Unable to close survey identified by `%s`: %s", id, reason));
        this.id = id;
    }

    @Override
    public String getDomain() {
        return SurveyConstants.DOMAIN_NAME;
    }

    /**
     * Returns the survey ID where the business rule has been violated.
     *
     * @return the identifier of the survey
     */
    @Override
    public Serializable getId() {
        return id;
    }
}
