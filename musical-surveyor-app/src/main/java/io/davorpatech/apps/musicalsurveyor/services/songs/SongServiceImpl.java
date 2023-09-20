package io.davorpatech.apps.musicalsurveyor.services.songs;

import io.davorpatech.apps.musicalsurveyor.domain.artist.ArtistConstants;
import io.davorpatech.apps.musicalsurveyor.domain.songs.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.ArtistRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.SongRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Artist;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Song;
import io.davorpatech.fwk.exception.EntityUsedByForeignsException;
import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.exception.NoSuchForeignalEntityException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link SongService}.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage {@link Song} data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class SongServiceImpl extends JpaBasedDataService<
        SongRepository,
        Long, Song, SongWithArtistDTO,
        FindSongsInput, CreateSongInput, UpdateSongInput>
    implements SongService // NOSONAR
{
    private final ArtistRepository artistRepository;

    /**
     * Constructs a new {@link SongServiceImpl} with the given arguments.
     *
     * @param songRepository     the song repository, never {@code null}
     * @param artistRepository   the artist repository, never {@code null}
     */
    SongServiceImpl(SongRepository songRepository,
                    ArtistRepository artistRepository)
    {
        super(songRepository, SongConstants.DOMAIN_NAME);
        Assert.notNull(artistRepository, "ArtistRepository must not be null!");
        this.artistRepository = artistRepository;
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(
            Sort.Order.asc("artist.id"),
            Sort.Order.asc("id"));
    }

    @Override
    protected Example<Song> determineFindFilters(@NonNull FindSongsInput query) {
        final Long artistId = query.getArtistId();
        if (artistId == null) return super.determineFindFilters(query);
        // build the probe entity to filter by artistId
        Artist artist = new Artist();
        artist.setId(artistId);
        Song song = new Song();
        song.setArtist(artist);
        return Example.of(song);
    }

    @Override
    protected @NonNull SongWithArtistDTO convertEntityToDto(@NonNull Song entity) {
        final SongWithArtistDTO.SongArtistDTO artist = Optional
            .ofNullable(entity.getArtist())
            .map(value -> new SongWithArtistDTO.SongArtistDTO(
                                value.getId(), value.getName()))
            .orElse(null);
        return new SongWithArtistDTO(
                entity.getId(), artist,
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getDuration(),
                entity.getGenre());
    }

    @Override
    protected Song convertCreateToEntity(CreateSongInput input) {
        // first find its artist
        final Long artistId = input.getArtistId();
        final Artist artist = artistRepository
            .findById(artistId)
            .orElseThrow(NoSuchForeignalEntityException.creater(
                ArtistConstants.DOMAIN_NAME, artistId));
        // then map song properties
        Song entity = new Song();
        entity.setTitle(input.getTitle());
        entity.setReleaseYear(input.getReleaseYear());
        entity.setDuration(input.getDuration());
        entity.setGenre(input.getGenre());
        artist.addSong(entity);
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Song entity, @NonNull UpdateSongInput input) {
        // first find its artist
        final Long currentArtistId = entity.getArtistId();
        final Long artistId = input.getArtistId();
        if (!Objects.equals(currentArtistId, artistId)) {
            // checking business rule to prevent transfer song between artists
            throw new UnableToTransferSongBetweenArtistsException(
                entity.getId(), currentArtistId, artistId);
        }
        final Artist artist = artistRepository
            .findById(artistId)
            .orElseThrow(NoSuchForeignalEntityException.creater(
                ArtistConstants.DOMAIN_NAME, artistId));
        // then map song properties
        entity.setArtist(artist);
        entity.setTitle(input.getTitle());
        entity.setReleaseYear(input.getReleaseYear());
        entity.setDuration(input.getDuration());
        entity.setGenre(input.getGenre());
    }

    @Override
    public @NonNull SongWithArtistDTO findInArtistRepertoire(
            @NonNull Long artistId, @NonNull Long songId) {
        // check if artist exists
        Optional.of(artistId)
            .map(artistRepository::existsById)
            .filter(Boolean.TRUE::equals)
            .orElseThrow(NoSuchForeignalEntityException.creater( // NOSONAR
                ArtistConstants.DOMAIN_NAME, artistId));
        // retrieve checking if song exists
        final Song entity = repository.findInArtistRepertoire(artistId, songId)
            .orElseThrow(NoSuchEntityException.creater(domainName, songId));
        return convertEntityToDto(entity);
    }

    @Override
    protected void checkEntityDeletion(Song entity) {
        ensureEmptyParticipationResponses(null, entity.getId());
    }

    @Transactional
    @Override
    public void deleteFromArtistRepertoire(
            @NonNull Long artistId, @NonNull Long songId) {
        // business rule: prevent remove song if it has participation responses
        ensureEmptyParticipationResponses(artistId, songId);
        // remove counting the number of deleted entities
        int affectedRows = repository.deleteFromArtistRepertoire(artistId, songId);
        if (affectedRows == 0) { // no deletion performed
            // test if reason is a missing artist or song
            if (artistRepository.existsById(artistId)) {
                throw new NoSuchEntityException(domainName, songId);
            }
            throw new NoSuchForeignalEntityException(ArtistConstants.DOMAIN_NAME, artistId);
        }
        if (affectedRows > 1) { // more than one deletion performed
            throw new IncorrectResultSizeDataAccessException(1, affectedRows);
        }
    }

    protected void ensureEmptyParticipationResponses(@Nullable Long artistId, @NonNull Long songId) {
        long count = artistId == null
            ? repository.countByParticipationResponses(songId)
            : repository.countByParticipationResponses(artistId, songId);
        if (count > 0) {
            throw new EntityUsedByForeignsException(
                domainName, songId, "musicpoll.SurveyParticipationResponse", count);
        }
    }
}
