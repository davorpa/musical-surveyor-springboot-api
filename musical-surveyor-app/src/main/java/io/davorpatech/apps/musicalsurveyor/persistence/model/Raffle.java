package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.RaffleConstants;
import io.davorpatech.apps.musicalsurveyor.domain.RaffleStatus;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The Raffle entity class.
 *
 * <p>A raffle is a draw between all participants that had sent their as
 * favorites songs as anwsers to a survey made by a radio station.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = RaffleConstants.DOMAIN_NAME)
@Table(name = "RAFFLE")
public class Raffle extends BaseEntity<Long> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 418020668455507366L;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    @NotNull(groups = { OnUpdate.class, OnCreate.class})
    private Long id;

    @Column(name = "status", length = RaffleConstants.STATUS_MAXLEN, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RaffleStatus status;

    @Column(name = "resolution_date", nullable = true)
    private LocalDateTime resolutionDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "id",
        foreignKey = @ForeignKey(name = "FK_raffle_survey_id"))
    @MapsId
    private Survey survey;

    @OneToMany(mappedBy = "raffle", fetch = FetchType.LAZY)
    @OrderBy("id.prizeId ASC")
    private Set<@Valid RafflePrize> prizes = new LinkedHashSet<>();

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "%s, surveyId=%s, status=%s, resolution_date='%s', prizes=%s",
            super.defineObjAttrs(), getSurveyId(), status, resolutionDate, prizes.size());
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
     * Gets the internal status of the raffle.
     *
     * @return the status of the raffle
     */
    public RaffleStatus getStatus() {
        return status;
    }

    /**
     * Sets the internal status of the raffle.
     *
     * @param status the status of the raffle to set
     */
    public void setStatus(RaffleStatus status) {
        this.status = status;
    }

    /**
     * Gets the date and time when the raffle was resolved, thus is, when
     * the winners were drawn.
     *
     * @return the date and time when the raffle was resolved
     */
    public LocalDateTime getResolutionDate() {
        return resolutionDate;
    }

    /**
     * Sets the date and time when the raffle was resolved, thus is, when
     * the winners were drawn.
     *
     * @param resolutionDate the date and time when the raffle was resolved
     */
    public void setResolutionDate(LocalDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    /**
     * Gets the survey that originated the raffle.
     *
     * @return the survey that originated the raffle
     * @see Survey
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Sets the survey that originated the raffle.
     *
     * @param survey the survey that originated the raffle
     * @see Survey
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * Gets the ID of the survey that originated the raffle.
     *
     * @return the ID of the survey that originated the raffle
     *
     * @see #getSurvey()
     * @see Survey#getId()
     */
    private Long getSurveyId() {
        Survey target = getSurvey();
        return target == null ? null : target.getId();
    }

    /**
     * Gets the prizes that will be drawn in the raffle.
     *
     * <p>The returned collection is an unmodifiable copy of the original.
     *
     * @return the prizes that will be drawn in the raffle
     *
     * @see RafflePrize
     */
    public Set<RafflePrize> getPrizes() {
        return Set.copyOf(prizes); // ensure immutability
    }

    /**
     * Sets the prizes that will be drawn in the raffle.
     *
     * @param prizes the prizes that will be drawn in the raffle, must not be {@code null}.
     *
     * @see RafflePrize
     */
    public void setPrizes(Set<RafflePrize> prizes) {
        this.prizes = Objects.requireNonNull(prizes, "prizes must not be null!");
    }

    /**
     * Adds a prize to the raffle.
     *
     * @param prize the prize to add, must not be {@code null}.
     *
     * @see RafflePrize
     */
    public void addPrize(RafflePrize prize) {
        Objects.requireNonNull(prize, "prize to add must not be null!");
        prizes.add(prize); // register
        prize.setRaffle(this); // link to this reference
    }

    /**
     * Removes a prize from the raffle.
     *
     * @param prize the prize to remove, must not be {@code null}.
     *
     * @see RafflePrize
     */
    public void removePrize(RafflePrize prize) {
        Objects.requireNonNull(prize, "prize to remove must not be null!");
        prizes.remove(prize); // unregister
        prize.unsetPrize(); // unlink this reference
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
