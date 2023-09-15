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
    @OrderBy("prize.monetaryValue DESC, prize.id ASC")
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

    public void setId(Long id) {
        this.id = id;
    }

    public RaffleStatus getStatus() {
        return status;
    }

    public void setStatus(RaffleStatus status) {
        this.status = status;
    }

    public LocalDateTime getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(LocalDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    private Long getSurveyId() {
        Survey target = getSurvey();
        return target == null ? null : target.getId();
    }

    public Set<RafflePrize> getPrizes() {
        return Set.copyOf(prizes); // ensure immutability
    }

    public void setPrizes(Set<RafflePrize> prizes) {
        this.prizes = Objects.requireNonNull(prizes, "prizes must not be null!");
    }

    public void addPrize(RafflePrize prize) {
        Objects.requireNonNull(prize, "prize to add must not be null!");
        prizes.add(prize); // register
        prize.setRaffle(this); // link to this reference
    }

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
