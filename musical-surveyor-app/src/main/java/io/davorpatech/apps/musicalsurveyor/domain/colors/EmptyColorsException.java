package io.davorpatech.apps.musicalsurveyor.domain.colors;

import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.ErrorDomain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when some operation needs at least one color
 * to be present, but none is found.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class EmptyColorsException extends PreconditionalException // NOSONAR
    implements ErrorDomain // NOSONAR
{
    /**
     * Constructs a new {@link EmptyColorsException} with the given arguments.
     *
     * @param message the detail message
     */
    public EmptyColorsException(String message) {
        super(message);
    }

    @Override
    public String getDomain() {
        return ColorConstants.DOMAIN_NAME;
    }
}
