package io.davorpatech.apps.musicalsurveyor.services.surveys;

import io.davorpatech.apps.musicalsurveyor.domain.surveys.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.SurveyRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Survey;
import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyConfig;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of {@link SurveyService}.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage {@link Survey} data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class SurveyServiceImpl extends JpaBasedDataService<
        SurveyRepository,
        Long, Survey, SurveyDTO,
        FindSurveysInput, CreateSurveyInput, UpdateSurveyInput>
    implements SurveyService // NOSONAR
{
/**
     * Constructs a new {@link SurveyServiceImpl} with the given arguments.
     *
     * @param surveyRepository the survey repository, never {@code null}
     */
    SurveyServiceImpl(SurveyRepository surveyRepository)
    {
        super(surveyRepository, SurveyConstants.DOMAIN_NAME);
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(
            Sort.Order.desc("status"),
            Sort.Order.asc("startDate"),
            Sort.Order.asc("endDate"));
    }

    @Override
    protected @NonNull SurveyDTO convertEntityToDto(@NonNull Survey entity) {
        SurveyConfigDTO config = Optional.ofNullable(entity.getConfig())
            .map(value -> new SurveyConfigDTO(
                value.getNumMaxParticipants(),
                value.getNumSurveyResponses()
            ))
            .orElse(null);
        return new SurveyDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getStatus(),
            entity.getStartDate(),
            entity.getEndDate(),
            config);
    }

    @Override
    protected @NonNull Survey convertCreateToEntity(@NonNull CreateSurveyInput input) {
        final SurveyConfig config = resolveSurveyConfigWithDefaults(input.getConfig());
        final Survey entity = new Survey();
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());
        entity.setStatus(SurveyStatus.PENDING);
        entity.setStartDate(input.getStartDate());
        entity.setEndDate(input.getEndDate());
        entity.getConfig().setNumMaxParticipants(config.getNumMaxParticipants());
        entity.getConfig().setNumSurveyResponses(config.getNumSurveyResponses());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(@NonNull Survey entity, @NonNull UpdateSurveyInput input) {
        final SurveyConfig config = resolveSurveyConfigWithDefaults(input.getConfig());
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());
        entity.setStartDate(input.getStartDate());
        entity.setEndDate(input.getEndDate());
        entity.getConfig().setNumMaxParticipants(config.getNumMaxParticipants());
        entity.getConfig().setNumSurveyResponses(config.getNumSurveyResponses());
    }

    protected @NonNull SurveyConfig resolveSurveyConfigWithDefaults(@Nullable SurveyConfigDTO config) {
        return new SurveyConfig(
            Optional.ofNullable(config)
                .map(SurveyConfigDTO::getNumMaxParticipants)
                .orElseGet(() -> environment.getProperty(
                    "app.survey.config.num-max-participants",
                    Integer.class, 50)),
            Optional.ofNullable(config)
                .map(SurveyConfigDTO::getNumNeededResponses)
                .orElseGet(() -> environment.getProperty(
                    "app.survey.config.num-needed-responses",
                    Integer.class, 3))
        );
    }
}
