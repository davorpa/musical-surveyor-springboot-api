package io.davorpatech.fwk.model.commands;

import io.davorpatech.fwk.model.ValueObject;

/**
 * Root interface for those value objects that acts as an input commands.
 *
 * <p>As a DTO, input commands should be remaining immutable: this is required
 * for the implicit contract that two value objects created equal, should remain
 * equal. It is also useful for value objects to be immutable, as client code
 * cannot put the value object in an invalid state or introduce buggy behaviour
 * after instantiation.
 *
 * <p>Moreover, input commands need {@link #hashCode()} and {@link #equals(Object)}
 * implementation using all its attributes.
 */
public interface InputCmd extends ValueObject // NOSONAR
{
    /**
     * Two instances representing a input command are equal if
     * implements {@link #hashCode()} and {@link #equals(Object)}
     * using all its attributes.
     *
     * @param obj the instance to compare with
     * @return {@code true} if are equivalent
     */
    @Override
    boolean equals(final Object obj);

    /**
     * Two instances representing a input command are equal if
     * implements {@link #hashCode()} and {@link #equals(Object)}
     * using all its attributes.
     *
     * @return the hashcode of this object
     */
    @Override
    int hashCode();
}
