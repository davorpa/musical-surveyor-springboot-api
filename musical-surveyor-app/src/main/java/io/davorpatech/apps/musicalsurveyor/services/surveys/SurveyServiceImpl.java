package io.davorpatech.apps.musicalsurveyor.services.surveys;

import io.davorpatech.apps.musicalsurveyor.domain.RaffleConstants;
import io.davorpatech.apps.musicalsurveyor.domain.colors.EmptyColorsException;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.EmptyRadioListenersException;
import io.davorpatech.apps.musicalsurveyor.domain.surveys.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.*;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Survey;
import io.davorpatech.apps.musicalsurveyor.persistence.model.SurveyConfig;
import io.davorpatech.fwk.exception.EntityUsedByForeignsException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;
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
    private final RadioListenerRepository radioListenerRepository;

    private final ColorRepository colorRepository;

    private final RaffleRepository raffleRepository;

    private final SurveyParticipationRepository surveyParticipationRepository;

    /**
     * Constructs a new {@link SurveyServiceImpl} with the given arguments.
     *
     * @param surveyRepository              the survey repository, never {@code null}
     * @param radioListenerRepository       the radio listener repository, never {@code null}
     * @param colorRepository               the color repository, never {@code null}
     * @param raffleRepository              the raffle repository, never {@code null}
     * @param surveyParticipationRepository the survey participation repository, never {@code null}
     */
    SurveyServiceImpl(SurveyRepository surveyRepository,
                      RadioListenerRepository radioListenerRepository,
                      ColorRepository colorRepository,
                      RaffleRepository raffleRepository,
                      SurveyParticipationRepository surveyParticipationRepository)
    {
        super(surveyRepository, SurveyConstants.DOMAIN_NAME);
        Assert.notNull(radioListenerRepository, "RadioListenerRepository must not be null!");
        this.radioListenerRepository = radioListenerRepository;
        Assert.notNull(colorRepository, "ColorRepository must not be null!");
        this.colorRepository = colorRepository;
        Assert.notNull(raffleRepository, "RaffleRepository must not be null!");
        this.raffleRepository = raffleRepository;
        Assert.notNull(surveyParticipationRepository, "SurveyParticipationRepository must not be null!");
        this.surveyParticipationRepository = surveyParticipationRepository;
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(
            Sort.Order.desc("startDate"),
            Sort.Order.asc("endDate"));
    }

    @Override
    protected Example<Survey> determineFindFilters(@NonNull FindSurveysInput query) {
        final SurveyStatus status = query.getStatus();
        if (status == null) return super.determineFindFilters(query);
        // build the probe entity to filter by status
        Survey probe = new Survey();
        probe.setStatus(status);
        return Example.of(probe);
    }

    @Transactional
    @Override
    public @NonNull SurveyDTO create(@NonNull CreateSurveyInput input) {
        // business rules: there must be at least one radio listener and one color
        if (radioListenerRepository.isEmpty()) {
            throw new EmptyRadioListenersException(
                "At least one radio listener must be present before create a survey");
        }
        if (colorRepository.isEmpty()) {
            throw new EmptyColorsException(
                "At least one color must be present before create a survey");
        }
        return super.create(input);
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
        // business rules: cannot edit a closed survey
        if (Objects.equals(entity.getStatus(), SurveyStatus.CLOSED)) {
            throw new UnableToEditLockedSurveyDetailException(entity.getId(), "Already closed");
        }

        final SurveyConfig config = resolveSurveyConfigWithDefaults(input.getConfig());
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());

        // business rule: cannot edit some survey attributes if participants has been already selected
        SurveyConfig currentConfig = entity.getConfig();
        LocalDateTime currentStartDate = entity.getStartDate();
        if (!Objects.equals(currentConfig.getNumMaxParticipants(), config.getNumMaxParticipants())
            || !Objects.equals(currentConfig.getNumSurveyResponses(), config.getNumSurveyResponses())
            || !Objects.equals(currentStartDate, input.getStartDate()))
        {
            if (surveyParticipationRepository.existsBySurvey(entity.getId())) { // NOSONAR
                throw new UnableToEditLockedSurveyDetailException(entity.getId(),
                    "Already selected participants");
            }
        }
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
                    Integer.class, SurveyConstants.CFG_NUM_MAX_PARTICIPANTS_DEFAULT)),
            Optional.ofNullable(config)
                .map(SurveyConfigDTO::getNumNeededResponses)
                .orElseGet(() -> environment.getProperty(
                    "app.survey.config.num-needed-responses",
                    Integer.class, SurveyConstants.CFG_NUM_RESPONSES_DEFAULT))
        );
    }

    @Override
    protected void checkEntityDeletion(Survey entity) {
        Long id = entity.getId();
        long count = -1;
        if ((count = raffleRepository.countBySurvey(id)) > 0) {
            throw new EntityUsedByForeignsException(domainName, id,
                RaffleConstants.DOMAIN_NAME, count);
        }
        if ((count = surveyParticipationRepository.countBySurvey(id)) > 0) {
            throw new EntityUsedByForeignsException(domainName, id,
                SurveyParticipationConstants.DOMAIN_NAME, count);
        }
    }
}
