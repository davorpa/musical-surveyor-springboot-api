package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.SurveyConstants;
import io.davorpatech.apps.musicalsurveyor.domain.SuveyStatus;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The Survey entity class.
 *
 * <p>In the context of a radio station, a survey is a set of questions
 * that are asked to the listeners in order to get their opinion about
 * their musical preferences. Each response is a song that the listener
 * likes.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = SurveyConstants.DOMAIN_NAME)
@Table(name = "SURVEY")
public class Survey extends BaseEntity<Long> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -6363601101197972405L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = {OnCreate.class})
    @NotNull(groups = {OnUpdate.class})
    private Long id;

    @Column(name = "title", length = SurveyConstants.TITLE_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = SurveyConstants.TITLE_MAXLEN)
    private String title;

    @Column(name = "description", length = SurveyConstants.DESCRIPTION_MAXLEN, nullable = true)
    @Size(max = SurveyConstants.DESCRIPTION_MAXLEN)
    private String description;

    @Column(name = "status", length = SurveyConstants.STATUS_MAXLEN, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private SuveyStatus status;

    @Column(name = "start_date", nullable = false)
    @NotNull
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull
    private LocalDateTime endDate;

    @Embedded
    @Valid
    private final SurveyConfig config = new SurveyConfig();

    @OneToOne(mappedBy = "survey", optional = true,
        cascade = CascadeType.ALL, orphanRemoval = true,
        fetch = FetchType.EAGER)
    @Valid
    private Raffle raffle;

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
    @OrderBy("participatedAt ASC, id.participantId ASC")
    private Set<@Valid SurveyParticipation> participations = new LinkedHashSet<>();

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "%s, title='%s', status=%s, startDate='%s', endDate='%s', config=%s, participations=%s",
            super.defineObjAttrs(), title, status, startDate, endDate, config, participations.size());
    }

    @Override
    public Long getId() {
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
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the short title of the survey.
     *
     * @return the title of the survey
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the short title of the survey.
     *
     * @param title the title of the survey to set, never {@code null}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the long description of the survey.
     *
     * @return the description of the survey
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the long description of the survey.
     *
     * @param description the description of the survey to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the internal state of the survey.
     *
     * @return the status of the survey
     */
    public SuveyStatus getStatus() {
        return status;
    }

    /**
     * Sets the internal state of the survey.
     *
     * @param status the status of the survey to set, never {@code null}
     */
    public void setStatus(SuveyStatus status) {
        this.status = status;
    }

    /**
     * Gets the date and time when the survey starts.
     *
     * @return the date and time when the survey starts
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the date and time when the survey starts.
     *
     * @param startDate the date and time when the survey starts to set,
     *                  never {@code null}
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the date and time when the survey ends.
     *
     * @return the date and time when the survey ends
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the date and time when the survey ends.
     *
     * @param endDate the date and time when the survey ends to set,
     *                never {@code null}
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the configuration parameters of the survey.
     *
     * @return the configuration parameters of the survey
     */
    public SurveyConfig getConfig() {
        return config;
    }

    /**
     * Gets the raffle for this survey.
     *
     * <p>It is a one-to-one relationship, so it is always a single raffle.
     * Raffles are optional, so it may be {@code null}, meaning that there
     * is no raffle for this survey. As a business rule, raffles should be
     * created and set when the survey pass to the {@link SuveyStatus#CLOSED}
     * status.
     *
     * @return the raffle for this survey
     */
    public Raffle getRaffle() {
        return raffle;
    }

    /**
     * Sets the raffle for this survey.
     *
     * <p><b>NOTE</b>: Since this is the owning side of the @{@link OneToOne}
     * relationship, is perfect to use this method as linker between one survey
     * and its associated raffle.
     *
     * <p>Raffles should be created and linked when the survey pass to the
     * {@link SuveyStatus#CLOSED} status.
     *
     * @param raffle the raffle for this survey
     */
    public void setRaffle(Raffle raffle) {
        if (raffle == null) { // unlink bidirectional relationship
            if (this.raffle != null) {
                // dispose previous references
                this.raffle.unsetSurvey();
            }
        } else { // link bidirectional relationship
            raffle.setSurvey(this);
        }
        this.raffle = raffle;
    }

    /**
     * Gets the participations of the survey.
     *
     * <p>It is a one-to-many relationship, so it is always a set of
     * participations. Participations are optional, so it may be empty,
     * meaning that there are no participations for this survey yet.
     *
     * <p>Participations are initialized when the administrator selects
     * aleatory the radio station listeners that will participate in the
     * survey.
     *
     * <p>The returned collection is unmodifiable copy of the original.
     *
     * @return the participations of the survey, never {@code null}
     */
    public Set<SurveyParticipation> getParticipations() {
        return Set.copyOf(participations);
    }

    /**
     * Sets the participations of the survey.
     *
     * @param participations the participations of the survey, must not be {@code null}.
     */
    public void setParticipations(Set<SurveyParticipation> participations) {
        this.participations = Objects.requireNonNull(
            participations, "participations must not be null!");
    }

    /**
     * Adds a participation to the survey.
     *
     * <p>As part of a bidirectional relationship, this method also sets the
     * survey of the participation.
     *
     * @param participation the participation to add, must not be {@code null}.
     */
    public void addParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to add must not be null!");
        participations.add(participation); // register
        participation.setSurvey(this); // link to this reference
    }

    /**
     * Removes a participation from the survey.
     *
     * <p>As part of a bidirectional relationship, this method also unsets the
     * survey of the participation.
     *
     * @param participation the participation to remove, must not be {@code null}.
     */
    public void removeParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to remove must not be null!");
        participations.remove(participation); // unregister
        participation.unsetSurvey(); // unlink this reference
    }

    @Override
    public Audit getAudit(){
        return audit;
    }
}
