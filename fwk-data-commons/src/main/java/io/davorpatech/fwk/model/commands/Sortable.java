package io.davorpatech.fwk.model.commands;

import org.springframework.data.domain.Sort;

/**
 * Marker interface to provide sort resolution to any {@link FindInputCmd}.
 *
 * @see FindInputCmd
 */
public interface Sortable
{
    /**
     * Return the sort holder used as part of any search query.
     *
     * @return the sort holder
     */
    Sort getSort();
}
