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
        fetch = FetchType.LAZY)
    private Raffle raffle;

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, title='%s', status=%s, startDate='%s', endDate='%s', config=%s",
            super.defineObjAttrs(), title, status, startDate, endDate, config);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SuveyStatus getStatus() {
        return status;
    }

    public void setStatus(SuveyStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public SurveyConfig getConfig() {
        return config;
    }

    public Raffle getRaffle() {
        return raffle;
    }

    public void setRaffle(Raffle raffle) {
        if (raffle == null) { // unlink bidirectional relationship
            if (this.raffle != null) {
                // dispose previous references
                this.raffle.setSurvey(null);
            }
        } else { // link bidirectional relationship
            raffle.setSurvey(this);
        }
        this.raffle = raffle;
    }

    @Override
    public Audit getAudit(){
        return audit;
    }
}
