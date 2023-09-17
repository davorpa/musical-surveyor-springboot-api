package io.davorpatech.apps.musicalsurveyor.services.artist;

import io.davorpatech.apps.musicalsurveyor.domain.artist.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.ArtistRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Artist;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /**
     * Constructs a new {@link ArtistServiceImpl} with the given arguments.
     *
     * @param artistRepository the artist repository, never {@code null}
     */
    ArtistServiceImpl(ArtistRepository artistRepository) {
        super(artistRepository, ArtistConstants.DOMAIN_NAME);
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
}
