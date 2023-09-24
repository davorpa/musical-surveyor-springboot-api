package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.surveys.SurveyConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * The SurveyConfig entity class.
 *
 * <p>It holds the configuration parameters of a {@link Survey} entity.
 *
 * @see Survey
 */
@Embeddable
public class SurveyConfig extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -6232890906214957251L;

    @Column(name = "num_max_participants", nullable = false)
    @NotNull
    @Min(SurveyConstants.CFG_NUM_MAX_PARTICIPANTS_MIN)
    private Integer numMaxParticipants;

    @Column(name = "num_survey_responses", nullable = false)
    @NotNull
    @Min(SurveyConstants.CFG_NUM_RESPONSES_MIN)
    private Integer numSurveyResponses;

    protected SurveyConfig() {
        super();
    }

    public SurveyConfig(Integer numMaxParticipants,
                        Integer numNeededResponses) {
        super();
        this.numMaxParticipants = numMaxParticipants;
        this.numSurveyResponses = numNeededResponses;
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("numMaxParticipants=%s, numSurveyResponses=%s",
            numMaxParticipants, numSurveyResponses);
    }

    /**
     * Gets the maximum number of participants allowed in the survey.
     *
     * <p>The default value is {@code 50}. It can be changed by the administrator
     * of the radio station when creating a new survey.
     *
     * @return the maximum number of participants allowed in the survey,
     *         never {@code null}
     */
    public Integer getNumMaxParticipants() {
        return numMaxParticipants;
    }

    /**
     * Sets the maximum number of participants allowed in the survey.
     *
     * <p>The default value is {@code 50}. It can be changed by the administrator
     * of the radio station when creating a new survey.
     *
     * @param numMaxParticipants the maximum number of participants allowed in the survey,
     *                           must not be {@code null}
     */
    public void setNumMaxParticipants(Integer numMaxParticipants) {
        this.numMaxParticipants = numMaxParticipants;
    }

    /**
     * Gets the number of survey responses to be collected.
     *
     * <p>The default value is {@code 3}. It can be changed by the administrator
     * of the radio station when creating a new survey.
     *
     * @return the number of survey responses to be collected,
     *         never {@code null}
     */
    public Integer getNumSurveyResponses() {
        return numSurveyResponses;
    }

    /**
     * Sets the number of survey responses to be collected.
     *
     * <p>The default value is {@code 3}. It can be changed by the administrator
     * of the radio station when creating a new survey.
     *
     * @param numSurveyResponses the number of survey responses to be collected,
     *                           must not be {@code null}
     */
    public void setNumSurveyResponses(Integer numSurveyResponses) {
        this.numSurveyResponses = numSurveyResponses;
    }
}
