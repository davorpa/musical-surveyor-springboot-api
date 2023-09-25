package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.ErrorDomain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when some operation needs at least one radio listener
 * to be present, but none is found.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class EmptyRadioListenersException extends PreconditionalException // NOSONAR
    implements ErrorDomain // NOSONAR
{
    /**
     * Constructs a new {@link EmptyRadioListenersException} with the given arguments.
     *
     * @param message the detail message
     */
    public EmptyRadioListenersException(String message) {
        super(message);
    }

    @Override
    public String getDomain() {
        return RadioListenerConstants.DOMAIN_NAME;
    }
}
