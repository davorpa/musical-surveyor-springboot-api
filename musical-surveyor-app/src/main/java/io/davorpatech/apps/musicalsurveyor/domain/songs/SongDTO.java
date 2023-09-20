package io.davorpatech.apps.musicalsurveyor.domain.songs;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Objects;

/**
 * The Song DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
@Schema(
    name = "Song",
    description = """
        In a context of a radio station, a song is a piece of music that can be
        played on the radio. It is usually a single track from an album.
        
        A song can only have one artist and an artist can have many songs.
        
        Audience members of a radio station can respond to a survey by selecting
        songs that they like and, a song can be selected by many audience members
        as the way to know their musical preferences."""
)
public class SongDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1025429157975169469L;

    @Schema(
        description = "The song ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The artist ID owning the song",
        example = "1")
    private final Long artistId;

    @Schema(
        description = "The song title",
        example = "Hey Jude")
    private final String title;

    @Schema(
        description = "The song release year",
        example = "1968")
    private final Integer releaseYear;

    @Schema(
        description = "The song duration in seconds",
        example = "431")
    private final Integer duration;

    @Schema(
        description = "The musical genre of the song",
        example = "Rock")
    private final String genre;

    /**
     * Constructs a new {@link SongDTO} with the given arguments.
     *
     * @param id       the song ID
     * @param artistId the artist ID owning the song
     * @param title    the song title
     * @param releaseYear the song release year
     * @param duration    the song duration in seconds
     * @param genre       the musical genre of the song
     */
    public SongDTO(Long id, Long artistId, String title, Integer releaseYear, Integer duration, String genre) {
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
        SongDTO other = (SongDTO) o;
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

    /**
     * Returns the song ID.
     *
     * @return the song ID
     */
    @Override
    public Long getId() {
        return id;
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
