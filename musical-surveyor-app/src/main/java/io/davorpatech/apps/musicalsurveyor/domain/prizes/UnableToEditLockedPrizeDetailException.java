package io.davorpatech.apps.musicalsurveyor.domain.prizes;


import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * Exception raised when we try to edit a prize detail, but the prize is locked
 * due to some business rule, such as the prize being already awarded.
 *
 * The prize ID and the reason why the prize is locked are provided.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class UnableToEditLockedPrizeDetailException extends PreconditionalException // NOSONAR
    implements Identifiable<Serializable>, ErrorDomain // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 2065792092394629439L;

    private final Serializable id;

    /**
     * Construct a {@code UnableToEditLockedPrizeDetailException} with the specified arguments.
     *
     * @param id     the prize ID we tried to edit
     * @param reason the reason why the prize is locked
     */
    public UnableToEditLockedPrizeDetailException(Serializable id, String reason)
    {
        super(String.format(
            "Unable to edit the prize detail identified by `%s` because it is locked: %s.",
            id, reason));
        this.id = id;
    }

    @Override
    public String getDomain() {
        return PrizeConstants.DOMAIN_NAME;
    }

/**
     * Returns the prize ID where the business rule has been violated.
     *
     * @return the identifier of the prize
     */
    @Override
    public Serializable getId() {
        return id;
    }
}
