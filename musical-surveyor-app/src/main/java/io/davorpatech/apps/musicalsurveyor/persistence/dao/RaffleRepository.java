package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code Raffle} repository interface.
 *
 * <p>As a repository, it provides access to the {@code Raffle} entities
 * in the database, including operations for saving, deleting,
 * and finding {@code Raffle} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see Raffle
 */
@Repository
@Transactional(readOnly = true)
public interface RaffleRepository extends JpaRepository<Raffle, Long>
{
    /**
     * Returns whether there are any raffles associated with the given {@code surveyId}.
     *
     * @param surveyId the survey ID to check, never {@code null}
     * @return {@code true} if there are any raffles associated with the given
     *         {@code surveyId}, {@code false} otherwise
     */
    default boolean existsBySurvey(Long surveyId) {
        return countBySurvey(surveyId) > 0L;
    }

    /**
     * Returns the number of raffles associated with the given {@code surveyId}.
     *
     * <p>Zero represents that there are no raffles associated with the given
     * {@code surveyId}, so it's safe to delete the survey.
     *
     * @param surveyId the survey ID to check, never {@code null}
     * @return the number of raffles associated with the given {@code surveyId},
     *         never {@code null}, always greater than or equal to 0
     */
    @Query("SELECT COUNT(sp) FROM #{#entityName} sp WHERE sp.survey.id = ?1")
    long countBySurvey(Long surveyId);
}
