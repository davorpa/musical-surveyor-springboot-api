package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyParticipation;
import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code SurveyParticipation} repository interface.
 *
 * <p>As a repository, it provides access to the {@code SurveyParticipation}
 * entities in the database, including operations for saving, deleting,
 * and finding {@code SurveyParticipation} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see SurveyParticipation
 * @see SurveyParticipationId
 */
@Repository
@Transactional(readOnly = true)
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation, SurveyParticipationId>
{
    /**
     * Returns whether there are any survey participations associated with the given {@code participantId}.
     *
     * @param participantId the participant ID to check, never {@code null}
     * @return {@code true} if there are any survey participations associated with the given
     *         {@code participantId}, {@code false} otherwise
     */
    default boolean existsByParticipant(Long participantId) {
        return countByParticipant(participantId) > 0;
    }

    /**
     * Returns the number of survey participations associated with the given {@code participantId}.
     *
     * <p>Zero represents that there are no survey participations associated with the given
     * {@code participantId}, so it's safe to delete the participant.
     *
     * @param participantId the participant ID to check, never {@code null}
     * @return the number of survey participations associated with the given {@code participantId},
     *         never {@code null}, always greater than or equal to 0
     */
    @Query("SELECT COUNT(sp) FROM #{#entityName} sp WHERE sp.participant.id = ?1")
    long countByParticipant(Long participantId);
}
