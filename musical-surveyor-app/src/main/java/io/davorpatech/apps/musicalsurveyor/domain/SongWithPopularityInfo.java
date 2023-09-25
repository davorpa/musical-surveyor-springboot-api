package io.davorpatech.apps.musicalsurveyor.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Projection for {@link io.davorpatech.apps.musicalsurveyor.persistence.model.Song}
 */
@Schema(
    name = "SongWithPopularityInfo",
    description = "Song with popularity information")
@JsonPropertyOrder({
    "id",
    "artistId",
    "artistName",
    "title",
    "releaseYear",
    "duration",
    "genre",
    "likes"
})
public interface SongWithPopularityInfo extends Identifiable<Long> // NOSONAR
{

    /**
     * Returns the ID of this song.
     *
     * @return the ID of this song
     */
    @Schema(
        name = "id",
        description = "The ID of the song",
        example = "1")
    @Override
    Long getId();


    @Schema(
        name = "artistId",
        description = "The ID of the artist",
        example = "1")
    Long getArtistId();

    @Schema(
        name = "artistName",
        description = "The name of the artist",
        example = "The Beatles")
    String getArtistName();

    /**
     * Returns the title of this song.
     *
     * @return the title of this song
     */
    @Schema(
        name = "title",
        description = "The title of the song",
        example = "Hey Jude")
    String getTitle();

    /**
     * Returns the release year of this song.
     *
     * @return the release year of this song
     */
    @Schema(
        name = "releaseYear",
        description = "The release year of the song",
        example = "1968")
    Integer getReleaseYear();

    /**
     * Returns the duration of this song in seconds.
     *
     * @return the duration of this song in seconds
     */
    @Schema(
        name = "duration",
        description = "The duration of the song in seconds",
        example = "431")
    Integer getDuration();

    /**
     * Returns the genre of this song.
     *
     * @return the genre of this song
     */
    @Schema(
        name = "genre",
        description = "The genre of the song",
        example = "Rock")
    String getGenre();

    /**
     * Returns the popularity rank, thus is, the number of times this song was selected
     * as a favorite in the successive surveys made by the radio station.
     *
     * @return the number of likes for this song
     */
    @Schema(
        name = "likes",
        description = """
            The number of times this song was selected as a favorite in the successive 
            surveys made by the radio station""",
        example = "10")
    long getLikes();
}
