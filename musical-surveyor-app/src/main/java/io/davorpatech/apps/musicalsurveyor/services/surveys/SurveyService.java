package io.davorpatech.apps.musicalsurveyor.services.surveys;

import io.davorpatech.apps.musicalsurveyor.domain.surveys.CreateSurveyInput;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.FindSurveysInput;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.SurveyDTO;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.UpdateSurveyInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Survey;
import io.davorpatech.fwk.service.data.DataService;

/**
 * Service for managing {@link Survey} data domain.
 */
public interface SurveyService extends DataService<
        Long, Survey, SurveyDTO,
        FindSurveysInput, CreateSurveyInput, UpdateSurveyInput> // NOSONAR
{

}
