package io.davorpatech.fwk.model.commands;

/**
 * Input Command DTO (Data Transfer Object) that defines the contract of
 * those data objects used to find a domain business entity of a concrete
 * kind.
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
public interface FindInputCmd extends InputCmd
{
    /**
     * Returns the number of the current page (zero-index based).
     *
     * @return the number of the current page
     */
    int getPageNumber();

    /**
     * Returns the page size; thus is, the number of items to be returned.
     *
     * <p>A size less than one is interpreted by the correspondent find service
     * as a non-paged search.
     *
     * @return the page size
     */
    int getPageSize();
}
