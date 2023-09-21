package io.davorpatech.apps.musicalsurveyor.services.songs;

import io.davorpatech.apps.musicalsurveyor.domain.songs.CreateSongInput;
import io.davorpatech.apps.musicalsurveyor.domain.songs.FindSongsInput;
import io.davorpatech.apps.musicalsurveyor.domain.songs.SongWithArtistDTO;
import io.davorpatech.apps.musicalsurveyor.domain.songs.UpdateSongInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Song;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.service.data.DataService;
import org.springframework.lang.NonNull;

/**
 * Service for managing {@link Song} data domain.
 */
public interface SongService extends DataService<
        Long, Song, SongWithArtistDTO,
        FindSongsInput, CreateSongInput, UpdateSongInput> // NOSONAR
{
    /**
     * Retrieves the detail of a song by its identifier if is part of
     * the given artist repertoire.
     *
     * @param artistId the identifier of the artist owner of the song
     * @param songId   the identifier of the song
     * @return the song with the given identifiers
     * @throws NoSuchEntityException if the record identified by the given
     *         {@literal ids} is not found
     */
    @NonNull
    SongWithArtistDTO findInArtistRepertoire(
        final @NonNull Long artistId, final @NonNull Long songId);

    /**
     * Removes a song by its identifier only if is part of the given
     * artist repertoire.
     *
     * @param artistId the identifier of the artist owner of the song
     * @param songId   the identifier of the song
     * @throws NoSuchEntityException if the record identified by the given
     *         {@literal ids} is not found
     */
    void deleteFromArtistRepertoire(
        final @NonNull Long artistId, final @NonNull Long songId);
}
