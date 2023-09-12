package io.davorpatech.fwk.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Root exception managed by the transactional layers of the application.
 *
 * @see io.davorpatech.fwk.service.Service
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class BusinessException extends NestedRuntimeException
{
    private static final long serialVersionUID = 8010191976099325282L;

    /**
     * Construct a {@code BusinessException} with the specified detail message.
     *
     * @param msg the detail message
     */
    protected BusinessException(final String msg)
    {
        super(msg);
    }

    /**
     * Construct a {@code BusinessException} with the specified detail message
     * and nested exception.
     *
     * @param msg the detail message
     * @param cause the nested exception
     */
    protected BusinessException(final String msg, final Throwable cause)
    {
        super(msg, cause);
    }
}
