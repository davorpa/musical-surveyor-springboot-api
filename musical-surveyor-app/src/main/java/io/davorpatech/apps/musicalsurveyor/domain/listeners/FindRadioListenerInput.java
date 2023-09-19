package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.model.commands.BaseFindInputCmd;
import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;

import java.io.Serial;

/**
 * Input object for finding {@code RadioListener} instances.
 *
 * <p>As a domain DTO, it follows the {@link BaseSortableFindInputCmd} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */

public class FindRadioListenerInput extends BaseSortableFindInputCmd {
    @Serial
    private static final long serialVersionUID = 5471516994682940700L;

    /**
     * Constructs a new {@link BaseFindInputCmd} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     */
    public FindRadioListenerInput(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
