package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyParticipation;
import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation, SurveyParticipationId>
{

}
