package io.davorpatech.apps.musicalsurveyor.domain.songs;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.io.Serial;

/**
 * Input object for finding {@code Song} instances.
 *
 * <p>Optionally, it can be used to find {@code Song} instances by artist ID.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class FindSongsInput extends BaseSortableFindInputCmd // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -5876522385896575289L;

    private final Long artistId;

    /**
     * Constructs a new {@link FindSongsInput} with the given arguments.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     * @param artistId   the artist ID to be used as part of any search query
     */
    FindSongsInput(int pageNumber, int pageSize, @Nullable Sort sort,
                   @Nullable Long artistId) {
        super(pageNumber, pageSize, sort);
        this.artistId = artistId;
    }

    /**
     * Returns the artist ID to be used as part of any search query.
     *
     * @return the artist ID to be used as part of any search query
     */
    public Long getArtistId() {
        return artistId;
    }

    /**
     * Builds a {@link FindSongsInput} for find all songs.
     *
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     * @return a new {@link FindSongsInput} with the given arguments
     */
    public static FindSongsInput of(
            int pageNumber, int pageSize, Sort sort) {
        return new FindSongsInput(pageNumber, pageSize, sort, null);
    }

    /**
     * Builds a {@link FindSongsInput} for find songs of a given artist.
     *
     * @param artistId   the artist ID owner of the songs to be found
     * @param pageNumber the number of the current page (zero-index based)
     * @param pageSize   the page size; thus is, the number of items to be returned
     * @param sort       the sort to be used as part of any search query
     * @return a new {@link FindSongsInput} with the given arguments
     */
    public static FindSongsInput ofArtist(
            Long artistId, int pageNumber, int pageSize, Sort sort) {
        return new FindSongsInput(pageNumber, pageSize, sort, artistId);
    }
}
