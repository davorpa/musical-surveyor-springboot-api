package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.RaffleTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The {@code RaffleTicket} repository interface.
 *
 * <p>As a repository, it provides access to the {@code RaffleTicket}
 * entities in the database, including operations for saving, deleting,
 * and finding {@code RaffleTicket} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see RaffleTicket
 */
@Repository
public interface RaffleTicketRepository extends JpaRepository<RaffleTicket, Long>
{
    /**
     * Returns whether there are any raffle tickets associated with the given {@code colorId}.
     *
     * @param colorId the color ID to check, never {@code null}
     * @return {@code true} if there are any raffle tickets associated with the given
     *         {@code colorId}, {@code false} otherwise
     */
    @Query("""
        SELECT CASE WHEN COUNT(rt) > 0 THEN TRUE ELSE FALSE END
        FROM #{#entityName} rt
        WHERE rt.color.id = ?1
        """)
    boolean existsByColor(Long colorId);

    /**
     * Returns the number of raffle tickets associated with the given {@code colorId}.
     *
     * @param colorId the color ID to check, never {@code null}
     * @return the number of raffle tickets associated with the given {@code colorId},
     *         never {@code null}, always greater than or equal to 0
     */
    @Query("SELECT COUNT(rt) FROM #{#entityName} rt WHERE rt.color.id = ?1")
    Long countByColor(Long colorId);
}
