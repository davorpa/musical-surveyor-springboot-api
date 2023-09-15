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

/**
 * The Prize entity class.
 *
 * <p>A prize is a reward that can be won by a raffle ticket after make a draw
 * between all participants that had sent their as favorites songs as anwsers
 * to a survey made by a radio station.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = PrizeConstants.DOMAIN_NAME)
@Table(
    name = "PRIZE",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_prize_title", columnNames = {"title"})
    }
)
public class Prize extends BaseEntity<Long> implements AuditAccessor // NOSONAR
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
     * Gets the title/name of the prize. It must be unique.
     *
     * @return the title of the prize
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title/name of the prize. It must be unique.
     *
     * @param title the title of the prize to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets a long description of the prize.
     *
     * @return the description of the prize
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a long description of the prize.
     *
     * <p>It can be used to describe the prize in detail, including its features,
     * its brand, its model, etc.
     *
     * @param description the description of the prize to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the monetary value of the prize.
     *
     * @return the monetary value of the prize
     */
    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    /**
     * Sets the monetary value of the prize.
     *
     * <p>It is the amount of money that the prize is worth.
     *
     * <p>It must be a positive number.
     *
     * @param monetaryValue the monetary value of the prize to set
     */
    public void setMonetaryValue(BigDecimal monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
