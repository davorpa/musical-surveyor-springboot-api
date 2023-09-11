package io.davorpatech.fwk.model;

/**
 * Marker interface to allow errors been human-friendly identifiable.
 */
public interface ErrorCode
{
    /**
     * Provides an app-specific error code to help find out exactly what happened.
     *
     * <p>It's a human-friendly identifier for a given exception.
     *
     * @return a short text code identifying the error, e.g. {@code NE-0001}
     */
    String getErrorCode();
}
