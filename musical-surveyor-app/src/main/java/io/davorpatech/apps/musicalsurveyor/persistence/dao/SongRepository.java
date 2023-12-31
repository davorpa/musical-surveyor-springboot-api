package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.domain.SongWithPopularityInfo;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Song;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The {@code Song} repository interface.
 *
 * <p>As a repository, it provides access to the {@code Song}
 * entities in the database, including operations for saving, deleting,
 * and finding {@code Song} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see Song
 */
@Repository
@Transactional(readOnly = true)
public interface SongRepository extends JpaRepository<Song, Long>
{
    /**
     * Returns whether there are any {@code Song} entities stored in
     * the repository.
     *
     * @return {@code true} if there are any {@code Song} entities,
     *         {@code false} otherwise
     *
     * @see #count()
     */
    default boolean isEmpty() {
        return count() == 0L;
    }

    /**
     * Returns the song with the given {@code artistId} and {@code songId}.
     *
     * @param artistId the artist ID to check, never {@code null}
     * @param songId   the song ID to check, never {@code null}
     * @return the song with the given {@code artistId} and {@code songId},
     *         never {@code null}
     */
    @Query("""
        SELECT s
        FROM #{#entityName} s
        WHERE s.id = :songId AND s.artist.id = :artistId
        """)
    @EntityGraph(
        attributePaths = { "artist" },
        type = EntityGraph.EntityGraphType.FETCH)
    Optional<Song> findInArtistRepertoire(
        @Param("artistId") Long artistId,
        @Param("songId") Long songId);

    /**
     * Deletes the song with the given {@code artistId} and {@code songId}.
     *
     * @param artistId the artist ID to check, never {@code null}
     * @param songId   the song ID to check, never {@code null}
     * @return the number of song deleted, always greater than or equal to zero,
     *         where zero indicates that no songs was deleted
     */
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM #{#entityName} s
        WHERE s.id = :songId AND s.artist.id = :artistId
        """)
    int deleteFromArtistRepertoire(
        @Param("artistId") Long artistId,
        @Param("songId") Long songId);

    /**
     * Returns whether there are any song associated with the given {@code artistId}.
     *
     * @param artistId the artist ID to check, never {@code null}
     * @return {@code true} if there are any song associated with the given
     *         {@code artistId}, {@code false} otherwise
     */
    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END
        FROM #{#entityName} s
        WHERE s.artist.id = ?1
        """)
    boolean existsByArtist(Long artistId);

    /**
     * Returns the number of song associated with the given {@code artistId}.
     *
     * @param artistId the artist ID to check, never {@code null}
     * @return the number of song associated with the given {@code artistId},
     *         never {@code null}, always greater than or equal to 0
     */
    @Query("SELECT COUNT(s) FROM #{#entityName} s WHERE s.artist.id = ?1")
    long countByArtist(Long artistId);

    /**
     * Returns the number of participation responses associated with the given
     * {@code songId} and {@code artistId}.
     *
     * @param songId   the song ID to check, never {@code null}
     * @param artistId the artist ID to check that owns the song, never {@code null}
     * @return the number of participation responses associated with the given
     *         parameters, wherein is always greater than or equal to zero.
     *         Zero indicates that no participation responses was found, situation
     *         permitted by the business rules that prevent remove a song with
     *         participation responses
     */
    @Query("""
         SELECT COUNT(s)
         FROM #{#entityName} s INNER JOIN s.participations sp
         WHERE s.id = :songId AND s.artist.id = :artistId
         """)
    long countByParticipationResponses(
        @Param("artistId") Long artistId,
        @Param("songId") Long songId);

    /**
     * Returns the number of participation responses associated with the given
     * {@code songId}.
     *
     * @param songId   the song ID to check, never {@code null}
     * @return the number of participation responses associated with the given
     *         parameters, wherein is always greater than or equal to zero.
     *         Zero indicates that no participation responses was found, situation
     *         permitted by the business rules that prevent remove a song with
     *         participation responses
     */
    @Query("""
         SELECT COUNT(s)
         FROM #{#entityName} s INNER JOIN s.participations sp
         WHERE s.id = :songId
         """)
    long countByParticipationResponses(
        @Param("songId") Long songId);

    /**
     * Returns a collection of songs ranked by popularity.
     *
     * <p>The rank is based on the number of times the song was selected as a favorite
     * in the successive surveys made by the radio station.
     *
     * @return the collection containing the ranked songs by popularity
     */
    @Query(value = """
        SELECT s.id AS id,
               s.title AS title,
               s.release_year AS releaseYear,
               s.duration AS duration,
               s.genre AS genre,
               a.id AS artistId,
               a.name AS artistName,
               COUNT(sr.song_id) AS likes
        FROM song s
             INNER JOIN artist a ON (s.artist_id = a.id)
             LEFT JOIN survey_responsing sr ON (s.id = sr.song_id)
        GROUP BY s.id, s.title, s.release_year, s.duration, s.genre, a.id, a.name
        ORDER BY likes DESC
        """, nativeQuery = true)
    List<SongWithPopularityInfo> findAllRankedPopularityBy();
}
