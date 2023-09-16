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

/**
 * The SurveyParticipation entity class.
 *
 * <p>A survey participation is a record of a listener that is participating
 * or has participated in a survey. It is a many-to-many relationship between
 * a survey and a radio listener.
 *
 * <p>When someone participates in a survey, thus is, sends their favourite songs
 * as a survey response, a @{@link RaffleTicket raffle ticket} is generated and
 * set joint with the {@link #participatedAt participation date and time} in this
 * survey participation record.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = SurveyParticipationConstants.DOMAIN_NAME)
@Table(
    name = "SURVEY_PARTICIPATION",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_survey_participation_raffle_ticket_id",
            columnNames = {"raffle_ticket_id"}
        )
    }
)
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

    @OneToOne(fetch = FetchType.LAZY, optional = true,
        cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
        name = "raffle_ticket_id",
        nullable = true,
        foreignKey = @ForeignKey(name = "FK_survey_participation_raffle_ticket_id"))
    @Valid
    private RaffleTicket raffleTicket;

    @Embedded
    private final Audit audit = new Audit();

    /**
     * Constructs a new {@code SurveyParticipation} instance without set
     * any properties.
     */
    SurveyParticipation() {
        super();
    }

    /**
     * Constructs a new {@code SurveyParticipation} instance with the given
     * properties set.
     *
     * <p>This properties to be set forms the composite primary key of this
     * entity.
     *
     * @param surveyId      the survey ID, part of the composite primary key
     * @param participantId the participant ID, part of the composite primary key
     */
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

    /**
     * Sets the ID of the entity.
     *
     * <p>It is not recommended to use this method directly, as it is
     * intended to be used by the persistence layer.
     *
     * @param id the ID of the entity to set
     */
    public void setId(SurveyParticipationId id) {
        this.id = id;
    }

    /**
     * Gets the ID of the survey.
     *
     * @return the ID of the survey
     */
    public Long getSurveyId() {
        SurveyParticipationId target = getId();
        return target == null ? null : target.getSurveyId();
    }

    /**
     * Gets the ID of the participant.
     *
     * @return the ID of the participant
     */
    public Long getParticipantId() {
        SurveyParticipationId target = getId();
        return target == null ? null : target.getParticipantId();
    }

    /**
     * Gets the survey.
     *
     * <p>Represents the single side of a many-to-one relationship with
     * the {@link Survey} entity.
     *
     * @return the survey
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Sets the survey.
     *
     * @param survey the survey to set, never {@code null}
     */
    public void setSurvey(Survey survey) {
        this.survey = Objects.requireNonNull(survey, "Survey must not be null!");
    }

    /**
     * Unsets the survey.
     */
    void unsetSurvey() {
        this.survey = null;
    }

    /**
     * Gets the participant.
     *
     * <p>Represents the single side of a many-to-one relationship with
     * the {@link RadioListener} entity.
     *
     * @return the participant
     */
    public RadioListener getParticipant() {
        return participant;
    }

    /**
     * Sets the participant.
     *
     * @param participant the participant to set, never {@code null}
     */
    public void setParticipant(RadioListener participant) {
        this.participant = Objects.requireNonNull(
            participant, "RadioListener participant must not be null!");
    }

    /**
     * Unsets the participant.
     */
    void unsetParticipant() {
        this.participant = null;
    }

    /**
     * Gets the date and time when the participant has sent their favourite
     * songs as a survey response.
     *
     * @return the date and time when the participant has participated in the survey
     *         if any, {@code null} otherwise
     */
    public LocalDateTime getParticipatedAt() {
        return participatedAt;
    }

    /**
     * Sets the date and time when the participant has sent their favourite
     * songs as a survey response.
     *
     * @param participatedAt the date and time when the participant has participated
     *                       in the survey to set
     */
    public void setParticipatedAt(LocalDateTime participatedAt) {
        this.participatedAt = participatedAt;
    }

    /**
     * Gets the raffle ticket associated with this survey participation.
     *
     * <p>This is the owning side of a one-to-one relationship, so it is always
     * a single raffle ticket.
     *
     * <p>It could be {@code null}, raffle tickets are assigned when a participant
     * submit their favourite songs as responses.
     *
     * @return the raffle ticket associated with this survey participation
     */
    public RaffleTicket getRaffleTicket() {
        return raffleTicket;
    }

    /**
     * Sets the raffle ticket associated with this survey participation.
     *
     * <p><b>NOTE</b>: Since this is the owning side of the @{@link OneToOne}
     * relationship, is perfect to use this method as linker between one raffle
     * ticket and its associated survey participation.
     *
     * <p>Raffle tickets are assigned when a participant submit their favourite
     * songs as responses.
     *
     * @param raffleTicket the raffle ticket associated with this survey participation
     */
    public void setRaffleTicket(RaffleTicket raffleTicket) {
        if (raffleTicket == null) { // unlink bidirectional relationship
            if (this.raffleTicket != null) {
                // dispose previous references
                this.raffleTicket.unsetSurveyParticipation();
            }
        } else { // link bidirectional relationship
            raffleTicket.setSurveyParticipation(this);
        }
        this.raffleTicket = raffleTicket;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
