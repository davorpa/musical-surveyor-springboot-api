package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners({
        AuditingEntityListener.class
})
@Entity
@Table(name = "RAFFLE")
public class Raffle  extends BaseEntity<Long> implements AuditAccessor {

    private static final long serialVersionUID = 418020668455507366L;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    @NotNull(groups = { OnUpdate.class, OnCreate.class})
    private Long id;

    @Column(name = "status",length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RaffleStatus status;

    @Column(name = "resolution_date", nullable = true)
    private LocalDateTime resolutionDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id",
            foreignKey = @ForeignKey(name = "FK_raffle_survey_id"))
    @MapsId
    private Survey survey;

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, survey_id=%s, status=%s, resolution_date='%s'",
                super.defineObjAttrs(), getSurveyId(), status, resolutionDate);
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

    @Override
    public Audit getAudit() {
        return audit;
    }
}
