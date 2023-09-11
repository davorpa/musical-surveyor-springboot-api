package io.davorpatech.fwk.model;

/**
 * Marker interface to allow errors been human-friendly identifiable
 * by their domain entity type.
 */
public interface ErrorDomain
{
    /**
     * Provides an app-specific domain to help find out exactly
     * on which entity type the error happened.
     *
     * <p>It's part of a human-friendly description for a given exception.
     *
     * @return a short text code identifying the domain entity where the error
     *         happens, e.g. {@code domain.superhero}
     */
    String getDomain();
}
