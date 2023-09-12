package io.davorpatech.fwk.model.commands;

import io.davorpatech.fwk.model.Identifiable;

import java.io.Serializable;

/**
 * Input Command DTO (Data Transfer Object) that defines the contract of
 * those data objects used to update a concrete domain business entity.
 *
 * <p>As a DTO, input commands should be remaining immutable: this is required
 * for the implicit contract that two value objects created equal, should remain
 * equal. It is also useful for value objects to be immutable, as client code
 * cannot put the value object in an invalid state or introduce buggy behaviour
 * after instantiation.
 *
 * <p>Moreover, input commands need {@link #hashCode()} and {@link #equals(Object)}
 * implementation using all its attributes.
 *
 * @param <ID> component type for the business entity ID.
 */
public interface UpdateInputCmd<ID extends Serializable> // NOSONAR
        extends InputCmd, Identifiable<ID> // NOSONAR
{

}
