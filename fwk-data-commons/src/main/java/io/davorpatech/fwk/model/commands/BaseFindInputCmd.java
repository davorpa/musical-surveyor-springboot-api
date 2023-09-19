package io.davorpatech.fwk.model.commands;

import io.davorpatech.fwk.model.BaseValueObject;

import java.util.Objects;

/**
 * Basic implementation of a {@link FindInputCmd}.
 *
 * @see FindInputCmd
 */
public class BaseFindInputCmd // NOSONAR
        extends BaseValueObject // NOSONAR
        implements FindInputCmd // NOSONAR
{
    private static final long serialVersionUID = 8735428136385642319L;

    private final int pageNumber;

    private final int pageSize;

    /**
     * Constructs a new {@link BaseFindInputCmd} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     *                   A size less than one is interpreted by the correspondent
     *                   find service as a non-paged search
     * @param pageSize   the page size; thus is, the number of items to be returned
     */
    public BaseFindInputCmd(int pageNumber, int pageSize) {
        super();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public BaseFindInputCmd() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseFindInputCmd other = (BaseFindInputCmd) o;
        return pageNumber == other.pageNumber &&
                pageSize == other.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("pageNumber=%s, pageSize=%s", pageNumber, pageSize);
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
