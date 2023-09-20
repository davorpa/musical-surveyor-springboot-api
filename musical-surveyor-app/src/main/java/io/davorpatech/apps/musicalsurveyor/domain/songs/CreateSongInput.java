package io.davorpatech.apps.musicalsurveyor.domain.songs;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.CreateInputCmd;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Input object for creating a new {@code Song}.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the presentation layer and the services layer. It is
 * also used to validate the data sent to the services layer. It is a
 * good practice to use different DTOs for each one of service operations.
 * This way, the DTOs can be extended in the future without breaking the
 * service contract.
 *
 * <p>This is why the {@link CreateSongInput} and the {@link UpdateSongInput}
 * are different classes. They both represent the same data, but the
 * {@link UpdateSongInput} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code Song}.
 * The {@code id} is not required to create a new {@code Song} because
 * the server will generate a new {@code id} for the new {@code Song}.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class CreateSongInput extends BaseValueObject implements CreateInputCmd // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 9210628566079600699L;

    @NotNull
    private final Long artistId;

    @NotBlank
    @Size(max = SongConstants.TITLE_MAXLEN)
    private final String title;

    @Min(SongConstants.RELEASE_YEAR_MIN)
    private final Integer releaseYear;

    @Min(SongConstants.DURATION_MIN)
    private final Integer duration;

    @Size(max = SongConstants.GENRE_MAXLEN)
    private final String genre;

    /**
     * Constructs a new {@link CreateSongInput} with the given arguments.
     *
     * @param artistId    the artist ID owning the song
     * @param title       the song title
     * @param releaseYear the song release year
     * @param duration    the song duration in seconds
     * @param genre       the musical genre of the song
     */
    public CreateSongInput(Long artistId, String title, Integer releaseYear, Integer duration, String genre) {
        super();
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
        CreateSongInput other = (CreateSongInput) o;
        return Objects.equals(artistId, other.artistId) &&
            Objects.equals(title, other.title) &&
            Objects.equals(releaseYear, other.releaseYear) &&
            Objects.equals(duration, other.duration) &&
            Objects.equals(genre, other.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, title, releaseYear, duration, genre);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "artistId=%s, title='%s', releaseYear=%s, duration=%s, genre=%s",
            artistId, title, releaseYear, duration,
            genre == null ? null : '\'' + genre + '\'');
    }

    /**
     * Returns the artist ID owning the song.
     *
     * @return the artist ID
     */
    public Long getArtistId() {
        return artistId;
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
