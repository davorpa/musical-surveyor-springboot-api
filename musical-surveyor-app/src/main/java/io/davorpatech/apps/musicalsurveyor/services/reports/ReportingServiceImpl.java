package io.davorpatech.apps.musicalsurveyor.services.reports;

import io.davorpatech.apps.musicalsurveyor.domain.SongWithPopularityInfo;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.SongRepository;
import io.davorpatech.fwk.service.ServiceCommonSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The {@code ReportingService} implementation.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class ReportingServiceImpl extends ServiceCommonSupport implements ReportingService // NOSONAR
{

    private final SongRepository songRepository;

    /**
     * Constructs a new {@link ReportingServiceImpl} with the given arguments.
     *
     * @param songRepository the song repository, never {@code null}
     */
    ReportingServiceImpl(SongRepository songRepository) {
        Assert.notNull(songRepository, "SongRepository must not be null!");
        this.songRepository = songRepository;
    }

    @Override
    public List<SongWithPopularityInfo> findAllMostPopularSongs() {
        return songRepository.findAllRankedPopularityBy();
    }
}
