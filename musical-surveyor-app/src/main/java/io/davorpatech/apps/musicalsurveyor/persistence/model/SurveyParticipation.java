package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.SurveyParticipationConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;

@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = SurveyParticipationConstants.DOMAIN_NAME)
@Table(name = "SURVEY_PARTICIPATION")
public class SurveyParticipation extends BaseEntity<SurveyParticipationId> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1370298980308163632L;

    @EmbeddedId
    @NotNull
    @Valid
    private SurveyParticipationId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "survey_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_survey_participation_survey_id"))
    @MapsId("surveyId")
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "participant_id",
        foreignKey = @ForeignKey(name = "FK_survey_participation_participant_id"))
    @MapsId("participantId")
    private RadioListener participant;

    @Column(name = "participated_at", nullable = true)
    private LocalDateTime participatedAt;

    @Embedded
    private final Audit audit = new Audit();

    SurveyParticipation() {
        super();
    }

    public SurveyParticipation(Long surveyId, Long participantId) {
        super();
        this.id = new SurveyParticipationId(surveyId, participantId);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, participatedAt='%s'", super.defineObjAttrs(), participatedAt);
    }

    @Override
    public SurveyParticipationId getId() {
        return id;
    }

    public void setId(SurveyParticipationId id) {
        this.id = id;
    }

    public Long getSurveyId() {
        SurveyParticipationId target = getId();
        return target == null ? null : target.getSurveyId();
    }

    public Long getParticipantId() {
        SurveyParticipationId target = getId();
        return target == null ? null : target.getParticipantId();
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = Objects.requireNonNull(survey, "Survey must not be null!");
    }

    void unsetSurvey() {
        this.survey = null;
    }

    public RadioListener getParticipant() {
        return participant;
    }

    public void setParticipant(RadioListener participant) {
        this.participant = Objects.requireNonNull(
            participant, "RadioListener participant must not be null!");
    }

    void unsetParticipant() {
        this.participant = null;
    }

    public LocalDateTime getParticipatedAt() {
        return participatedAt;
    }

    public void setParticipatedAt(LocalDateTime participatedAt) {
        this.participatedAt = participatedAt;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
