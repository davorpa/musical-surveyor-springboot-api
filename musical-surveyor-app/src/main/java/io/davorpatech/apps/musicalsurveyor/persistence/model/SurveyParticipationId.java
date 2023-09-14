package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

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

    SurveyParticipationId() {
        super();
    }

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

    public Long getSurveyId() {
        return surveyId;
    }

    public Long getParticipantId() {
        return participantId;
    }
}
