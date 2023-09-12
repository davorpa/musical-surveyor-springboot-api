package io.davorpatech.fwk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collection;

/**
 * A paged result is a slice of a list of objects.
 *
 * <p>It allows gain information about the position of it in the containing entire list.
 *
 * @param <T> the component type of the paged data
 */
public class PagedResult<T extends Serializable> implements Serializable // NOSONAR
{
    private static final long serialVersionUID = 1924584331116408492L;

    private final Collection<T> data;

    private final long totalElements;

    private final int pageNumber;

    private final int totalPages;

    @JsonProperty("isFirst")
    private final boolean first;
    @JsonProperty("isLast")
    private final boolean last;
    @JsonProperty("hasNext")
    private final boolean hasNext;
    @JsonProperty("hasPrevious")
    private final boolean hasPrevious;

    public PagedResult( // NOSONAR
            final Collection<T> data,
            final long totalElements,
            final int pageNumber, final int totalPages,
            final boolean first,
            final boolean last,
            final boolean hasNext,
            final boolean hasPrevious)
    {
        this.data = data;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    /**
     * Returns the page content as {@link Collection}.
     *
     * @return
     */
    public Collection<T> getData() {
        return data;
    }

    /**
     * Returns the total amount of elements. Is always non-negative.
     *
     * @return the total amount of elements
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Returns the number of the current page. Is always non-negative.
     *
     * @return the number of the current page
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Returns the number of total pages. Is always non-negative.
     *
     * @return the number of total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Returns whether the current page is the first one.
     *
     * @return {@code true} if the current page is the first one
     */
    public boolean isFirst() {
        return first;
    }

    /**
     * Returns whether the current page is the last one.
     *
     * @return {@code true} if the current page is the last one
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Returns if there is a next page.
     *
     * @return {@code true} if there is a next page
     */
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * Returns if there is a previous page.
     *
     * @return {@code true} if there is a previous page
     */
    public boolean hasPrevious() {
        return hasPrevious;
    }
}
