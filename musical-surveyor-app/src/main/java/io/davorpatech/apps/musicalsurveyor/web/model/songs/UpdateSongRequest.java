package io.davorpatech.apps.musicalsurveyor.web.model.songs;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.davorpatech.apps.musicalsurveyor.domain.songs.SongConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Represents the HTTP request body to update an existing {@code Song}.
 *
 * <p>Request DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the client and the server. It is also used to validate
 * the data sent to the server. It is a good practice to use different
 * DTOs for the request and the response. This way, the response DTO
 * can be extended in the future without breaking the client. This
 * is not the case for the request DTO, which should be kept as simple
 * as possible.
 *
 * <p>This is why the {@link CreateSongRequest} and the {@link UpdateSongRequest}
 * are different classes. They both represent the same data, but the
 * {@link UpdateSongRequest} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code Song}. The
 * {@code id} is not required to create a new {@code Song} because the server
 * will generate a new {@code id} for the new {@code Song}.
 */
@Schema(
    name = "UpdateSongRequest",
    description = "Represents the HTTP request body to update an existing Song."
)
public class UpdateSongRequest extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -318529779235074044L;

    @Schema(
        description = "The song ID",
        example = "1",
        hidden = true)
    private final Long id;

    @Schema(
        description = "The artist ID",
        example = "1",
        hidden = true)
    private final Long artistId;

    @Schema(
        description = "The song title",
        example = "Yesterday")
    @NotBlank
    @Size(max = SongConstants.TITLE_MAXLEN)
    private final String title;

    @Schema(
        description = "The song release year",
        example = "1965")
    @Min(SongConstants.RELEASE_YEAR_MIN)
    private final Integer releaseYear;

    @Schema(
        description = "The song duration in seconds",
        example = "120")
    @Min(SongConstants.DURATION_MIN)
    private final Integer duration;

    @Schema(
        description = "The song genre",
        example = "Rock")
    @Size(max = SongConstants.GENRE_MAXLEN)
    private final String genre;

    /**
     * Constructs a new {@link UpdateSongRequest} with the given
     * arguments.
     *
     * @param id           the song ID
     * @param artistId     the artist ID
     * @param title        the song title
     * @param releaseYear  the song release year
     * @param duration     the song duration
     * @param genre        the song genre
     */
    @JsonCreator
    public UpdateSongRequest(Long id, Long artistId,
                             String title, Integer releaseYear, Integer duration, String genre) {
        super();
        this.id = id;
        this.artistId = artistId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSongRequest other = (UpdateSongRequest) o;
        return Objects.equals(id, other.id) &&
            Objects.equals(artistId, other.artistId) &&
            Objects.equals(title, other.title) &&
            Objects.equals(releaseYear, other.releaseYear) &&
            Objects.equals(duration, other.duration) &&
            Objects.equals(genre, other.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artistId, title, releaseYear, duration, genre);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "id=%s, artistId=%s, title='%s', releaseYear=%s, duration=%s, genre=%s",
            id, artistId, title, releaseYear, duration,
            genre == null ? null : '\'' + genre + '\'');
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Returns the artist ID.
     *
     * @return the artist ID
     */
    public Long getArtistId() {
        return artistId;
    }

    public boolean hasArtistId() {
        return artistId != null;
    }

    /**
     * Returns the song title.
     *
     * @return the song title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the song release year.
     *
     * @return the song release year
     */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     * Returns the song duration in seconds.
     *
     * @return the song duration in seconds
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Returns the musical genre of the song.
     *
     * @return the musical genre of the song
     */
    public String getGenre() {
        return genre;
    }
}
