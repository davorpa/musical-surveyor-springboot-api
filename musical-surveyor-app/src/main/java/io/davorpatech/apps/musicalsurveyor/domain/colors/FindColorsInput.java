package io.davorpatech.apps.musicalsurveyor.domain.colors;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;

import java.io.Serial;

/**
 * Input object for finding {@code Color} instances.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class FindColorsInput extends BaseSortableFindInputCmd // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 850499376698354251L;

    /**
     * Constructs a new {@link FindColorsInput} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     */
    public FindColorsInput(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
