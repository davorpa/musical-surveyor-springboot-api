package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.PrizeConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.math.BigDecimal;

@EntityListeners({
        AuditingEntityListener.class
})
@Entity(name = PrizeConstants.DOMAIN_NAME)
@Table(
        name = "PRIZE",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_prize_title",
                        columnNames = {"title"}
                )
        }
)
public class Prize // NOSONAR
        extends BaseEntity<Long> // NOSONAR
        implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 7324518886994447461L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "title", length = PrizeConstants.TITLE_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = PrizeConstants.TITLE_MAXLEN)
    private String title;

    @Column(name = "description", length = PrizeConstants.DESCRIPTION_MAXLEN, nullable = true)
    @Size(max = PrizeConstants.DESCRIPTION_MAXLEN)
    private String description;

    @Column(name = "monetary_value", nullable = false,
        precision = PrizeConstants.MONETARY_VALUE_PRECISION,
        scale = PrizeConstants.MONETARY_VALUE_SCALE)
    @NotNull
    @Min(PrizeConstants.MONETARY_VALUE_MIN)
    private BigDecimal monetaryValue;

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, title='%s', monetaryValue=%s",
                super.defineObjAttrs(), title, monetaryValue);
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

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(BigDecimal monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
