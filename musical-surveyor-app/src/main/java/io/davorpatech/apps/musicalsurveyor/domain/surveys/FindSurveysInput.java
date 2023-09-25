package io.davorpatech.apps.musicalsurveyor.domain.surveys;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.io.Serial;

/**
 * Input object for finding {@code Survey} instances.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class FindSurveysInput extends BaseSortableFindInputCmd // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 1587501021393322699L;

    private final SurveyStatus status;

    /**
     * Constructs a new {@link FindSurveysInput} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     * @param status     the status of the surveys used as filter (optional)
     */
    public FindSurveysInput(int pageNumber, int pageSize, @Nullable Sort sort,
                            @Nullable SurveyStatus status) {
        super(pageNumber, pageSize, sort);
        this.status = status;
    }

    /**
     * Returns the status of the surveys used as filter.
     *
     * @return the status of the surveys used as filter
     */
    public @Nullable SurveyStatus getStatus() {
        return status;
    }
}
