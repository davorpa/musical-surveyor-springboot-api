package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    Long countByArtist(Long artistId);
}
