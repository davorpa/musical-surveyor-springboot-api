package io.davorpatech.apps.musicalsurveyor.domain.surveys;


import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * Exception raised when we try to edit a survey detail, but the survey is locked
 * due to some business rule, such as the survey is already closed.
 *
 * The survey ID and the reason why the prize is locked are provided.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class UnableToEditLockedSurveyDetailException extends PreconditionalException // NOSONAR
    implements Identifiable<Serializable>, ErrorDomain // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1542174043136877590L;

    private final Serializable id;

    /**
     * Construct a {@code UnableToEditLockedSurveyDetailException} with the specified arguments.
     *
     * @param id     the survey ID we tried to edit
     * @param reason the reason why the prize is locked
     */
    public UnableToEditLockedSurveyDetailException(Serializable id, String reason)
    {
        super(String.format(
            "Unable to edit the survey detail identified by `%s` because is locked: %s.",
            id, reason));
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
