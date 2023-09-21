package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.RafflePrize;
import io.davorpatech.apps.musicalsurveyor.persistence.model.RafflePrizeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code RafflePrize} repository interface.
 *
 * <p>As a repository, it provides access to the {@code RafflePrize}
 * entities in the database, including operations for saving, deleting,
 * and finding {@code RafflePrize} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see RafflePrize
 * @see RafflePrizeId
 */
@Repository
@Transactional(readOnly = true)
public interface RafflePrizeRepository extends JpaRepository<RafflePrize, RafflePrizeId>
{
    /**
     * Returns whether there are any raffle prizes associated with the given {@code prizeId}.
     *
     * @param prizeId the prize ID to check, never {@code null}
     * @return {@code true} if there are any raffle prizes associated with the given
     *         {@code prizeId}, {@code false} otherwise
     */
    default boolean existsByPrize(Long prizeId) {
        return countByPrize(prizeId) > 0;
    }

    /**
     * Returns the number of raffle prizes associated with the given {@code prizeId}.
     *
     * <p>Zero represents that there are no raffle prizes associated with the given
     * {@code prizeId}, so it's safe to delete the prize.
     *
     * @param prizeId the prize ID to check, never {@code null}
     * @return the number of raffle prizes associated with the given {@code prizeId},
     *         never {@code null}, always greater than or equal to 0
     */
    @Query("SELECT COUNT(rp) FROM #{#entityName} rp WHERE rp.prize.id = ?1")
    long countByPrize(Long prizeId);
}
