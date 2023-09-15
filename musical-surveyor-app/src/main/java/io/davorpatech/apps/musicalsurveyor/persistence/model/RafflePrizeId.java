package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * The RafflePrizeId entity class.
 *
 * <p>It represents the composite primary key of the {@link RafflePrize}
 * entity.
 *
 * <p>As a value object, follows the {@link BaseValueObject} contract, which
 * means that it has no ID, and it can be compared for equality to other value
 * objects using its attributes.
 *
 * @see RafflePrize
 */
@Embeddable
public class RafflePrizeId extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 3484439340899265401L;

    @Column(name = "raffle_id", nullable = false)
    @NotNull
    private Long raffleId;

    @Column(name = "prize_id", nullable = false)
    @NotNull
    private Long prizeId;

    /**
     * Constructs a new {@link RafflePrizeId} instance with no values.
     */
    RafflePrizeId() {
        super();
    }

    /**
     * Constructs a new {@link RafflePrizeId} instance with the given values
     * that forms the composite primary key of the {@link RafflePrize} entity
     *
     * @param raffleId the raffle ID to set
     * @param prizeId  the prize ID to set
     */
    public RafflePrizeId(Long raffleId, Long prizeId) {
        super();
        this.raffleId = raffleId;
        this.prizeId = prizeId;
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("raffleId=%s, prizeId=%s", getRaffleId(), getPrizeId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RafflePrizeId other = (RafflePrizeId) o;
        return Objects.equals(getRaffleId(), other.getRaffleId()) &&
                Objects.equals(getPrizeId(), other.getPrizeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRaffleId(), getPrizeId());
    }

    /**
     * Gets the raffle ID part of the composite primary key.
     *
     * @return the raffle ID
     */
    public Long getRaffleId() {
        return raffleId;
    }

    /**
     * Gets the prize ID part of the composite primary key.
     *
     * @return the prize ID
     */
    public Long getPrizeId() {
        return prizeId;
    }
}
