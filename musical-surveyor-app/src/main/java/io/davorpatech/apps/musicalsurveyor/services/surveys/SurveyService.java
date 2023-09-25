package io.davorpatech.apps.musicalsurveyor.services.surveys;

import io.davorpatech.apps.musicalsurveyor.domain.surveys.CreateSurveyInput;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.FindSurveysInput;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.SurveyDTO;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.UpdateSurveyInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Survey;
import io.davorpatech.fwk.service.data.DataService;
import org.springframework.lang.NonNull;

/**
 * Service for managing {@link Survey} data domain.
 */
public interface SurveyService extends DataService<
        Long, Survey, SurveyDTO,
        FindSurveysInput, CreateSurveyInput, UpdateSurveyInput> // NOSONAR
{

    /**
     * Closes the survey with the given {@code id}.
     *
     * <p>Closed surveys are frozen resources that cannot be updated or deleted.
     *
     * <p>Surveys can be manually closed only if its end date has been reached
     * and there are some participant who have completed the survey sending their
     * favourite songs. Closing surveys already closed has no effect.
     *
     * <p>Only surveys in this final state are eligible for raffles or prize draws.
     *
     * @param id the id of the survey to close
     * @return the closed survey
     */
    @NonNull SurveyDTO close(@NonNull Long id);
}
