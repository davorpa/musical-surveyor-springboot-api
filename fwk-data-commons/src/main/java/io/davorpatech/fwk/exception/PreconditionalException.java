package io.davorpatech.fwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A business exception indicating that a loginc precondition
 * has been violated.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public abstract class PreconditionalException extends BusinessException
{
    private static final long serialVersionUID = 8259536951720597941L;

    /**
     * Construct a {@code PreconditionalException} with the specified
     * detail message.
     *
     * @param msg the detail message
     */
    protected PreconditionalException(final String msg)
    {
        super(msg);
    }

    /**
     * Construct a {@code PreconditionalException} with the specified
     * detail message and nested exception.
     *
     * @param msg the detail message
     * @param cause the nested exception
     */
    protected PreconditionalException(final String msg, final Throwable cause)
    {
        super(msg, cause);
    }
}
