package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * The SurveyParticipationId entity class.
 *
 * <p>It represents the composite primary key of the {@link SurveyParticipation}
 * entity.
 *
 * <p>As a value object, follows the {@link BaseValueObject} contract, which
 * means that it has no ID, and it can be compared for equality to other value
 * objects using its attributes.
 *
 * @see SurveyParticipation
 */
@Embeddable
public class SurveyParticipationId extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 5530778427549156468L;

    @Column(name = "survey_id", nullable = false)
    @NotNull
    private Long surveyId;

    @Column(name = "participant_id", nullable = false)
    @NotNull
    private Long participantId;

    /**
     * Constructs a new {@link SurveyParticipationId} instance with no values.
     */
    SurveyParticipationId() {
        super();
    }

    /**
     * Constructs a new {@link SurveyParticipationId} instance with the given values
     * that forms the composite primary key of the {@link SurveyParticipation} entity
     *
     * @param surveyId      the survey ID to set
     * @param participantId the participant ID to set
     */
    public SurveyParticipationId(Long surveyId, Long participantId) {
        super();
        this.surveyId = surveyId;
        this.participantId = participantId;
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("surveyId=%s, participantId=%s", getSurveyId(), getParticipantId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyParticipationId other = (SurveyParticipationId) o;
        return Objects.equals(getSurveyId(), other.getSurveyId()) &&
                Objects.equals(getParticipantId(), other.getParticipantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurveyId(), getParticipantId());
    }

    /**
     * Returns the survey ID, part of the composite primary key.
     *
     * @return the survey ID
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * Returns the participant ID, part of the composite primary key.
     *
     * @return the participant ID
     */
    public Long getParticipantId() {
        return participantId;
    }
}
