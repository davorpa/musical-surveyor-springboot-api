package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyParticipation;
import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation, SurveyParticipationId>
{

}
