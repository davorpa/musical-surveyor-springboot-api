package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

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

    RafflePrizeId() {
        super();
    }

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

    public Long getRaffleId() {
        return raffleId;
    }

    public Long getPrizeId() {
        return prizeId;
    }
}
