package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;

import java.io.Serial;

/**
 * Input object for finding {@code Prize} instances.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class FindPrizesInput extends BaseSortableFindInputCmd // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -4234537729194260666L;

    /**
     * Constructs a new {@link FindPrizesInput} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     */
    public FindPrizesInput(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
