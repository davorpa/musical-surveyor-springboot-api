package io.davorpatech.apps.musicalsurveyor.services.artist;

import io.davorpatech.apps.musicalsurveyor.domain.artist.ArtistDTO;
import io.davorpatech.apps.musicalsurveyor.domain.artist.CreateArtistInput;
import io.davorpatech.apps.musicalsurveyor.domain.artist.FindArtistsInput;
import io.davorpatech.apps.musicalsurveyor.domain.artist.UpdateArtistInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Artist;
import io.davorpatech.fwk.service.data.DataService;

/**
 * Service for managing {@link Artist} data domain.
 */
public interface ArtistService extends DataService<
        Long, Artist, ArtistDTO,
        FindArtistsInput, CreateArtistInput, UpdateArtistInput> // NOSONAR
{

}
