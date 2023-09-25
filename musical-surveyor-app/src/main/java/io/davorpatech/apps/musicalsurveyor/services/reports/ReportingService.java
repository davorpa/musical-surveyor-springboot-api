package io.davorpatech.apps.musicalsurveyor.services.reports;

import io.davorpatech.apps.musicalsurveyor.domain.SongWithPopularityInfo;
import io.davorpatech.fwk.service.Service;

import java.util.List;

/**
 * The {@code ReportingService} interface.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage reporting data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
public interface ReportingService extends Service // NOSONAR
{
    /**
     * Returns a collection of songs ranked by popularity.
     *
     * <p>The rank is based on the number of times the song was selected as a favorite
     * in the successive surveys made by the radio station.
     *
     * @return the most popular songs
     */
    List<SongWithPopularityInfo> findAllMostPopularSongs();
}
