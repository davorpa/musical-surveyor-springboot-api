package io.davorpatech.apps.musicalsurveyor.services.artist;

import io.davorpatech.apps.musicalsurveyor.domain.artist.*;
import io.davorpatech.apps.musicalsurveyor.domain.songs.SongConstants;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.ArtistRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.SongRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Artist;
import io.davorpatech.fwk.exception.EntityUsedByForeignsException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Implementation of {@link ArtistService}.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage {@link Artist} data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class ArtistServiceImpl extends JpaBasedDataService<
        ArtistRepository,
        Long, Artist, ArtistDTO,
        FindArtistsInput, CreateArtistInput, UpdateArtistInput>
    implements ArtistService // NOSONAR
{
    private final SongRepository songRepository;

    /**
     * Constructs a new {@link ArtistServiceImpl} with the given arguments.
     *
     * @param artistRepository the artist repository, never {@code null}
     * @param songRepository   the song repository, never {@code null}
     */
    ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository)
    {
        super(artistRepository, ArtistConstants.DOMAIN_NAME);
        Assert.notNull(songRepository, "SongRepository must not be null!");
        this.songRepository = songRepository;
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(Sort.Order.asc("id"));
    }

    @Override
    protected @NonNull ArtistDTO convertEntityToDto(@NonNull Artist entity) {
        return new ArtistDTO(
                entity.getId(),
                entity.getName(),
                entity.getBiography());
    }

    @Override
    protected Artist convertCreateToEntity(CreateArtistInput input) {
        Artist entity = new Artist();
        entity.setName(input.getName());
        entity.setBiography(input.getBiography());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Artist entity, @NonNull UpdateArtistInput input) {
        entity.setName(input.getName());
        entity.setBiography(input.getBiography());
    }

    @Override
    protected void checkEntityDeletion(@NonNull Artist entity) {
        Long id = entity.getId();
        Long count = songRepository.countByArtist(id);
        if (count > 0) {
            throw new EntityUsedByForeignsException(
                ArtistConstants.DOMAIN_NAME, id, SongConstants.DOMAIN_NAME, count);
        }
    }
}
