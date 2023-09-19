package io.davorpatech.fwk.model.commands;

import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Basic implementation of a {@link FindInputCmd} with sorting capabilities.
 *
 * @see FindInputCmd
 * @see Sortable
 */
public class BaseSortableFindInputCmd // NOSONAR
        extends BaseFindInputCmd // NOSONAR
        implements Sortable // NOSONAR
{
    private static final long serialVersionUID = 2678552631195763065L;

    private final Sort sort;


    /**
     * Constructs a new {@link BaseFindInputCmd} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     */
    public BaseSortableFindInputCmd(int pageNumber, int pageSize, @Nullable Sort sort) {
        super(pageNumber, pageSize);
        this.sort = sort == null ? Sort.unsorted() : sort;
    }

    public BaseSortableFindInputCmd() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseSortableFindInputCmd other = (BaseSortableFindInputCmd) o;
        return Objects.equals(sort, other.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sort);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, sort=%s", super.defineObjAttrs(), sort);
    }

    @Override
    public Sort getSort() {
        return sort;
    }
}
