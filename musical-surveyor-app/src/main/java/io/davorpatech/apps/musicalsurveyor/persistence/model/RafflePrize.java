package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.RafflePrizeConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.util.Objects;

@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = RafflePrizeConstants.DOMAIN_NAME)
@Table(name = "RAFFLE_PRIZE")
public class RafflePrize extends BaseEntity<RafflePrizeId> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -4609151195613556396L;

    @EmbeddedId
    @NotNull
    @Valid
    private RafflePrizeId id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "raffle_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_raffle_prize_raffle_id"))
    @MapsId("raffleId")
    private Raffle raffle;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "prize_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_raffle_prize_prize_id"))
    @MapsId("prizeId")
    private Prize prize;

    @Embedded
    private final Audit audit = new Audit();

    RafflePrize() {
        super();
    }

    public RafflePrize(Long raffleId, Long prizeId) {
        super();
        this.id = new RafflePrizeId(raffleId, prizeId);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s", super.defineObjAttrs());
    }

    @Override
    public RafflePrizeId getId() {
        return id;
    }

    public void setId(RafflePrizeId id) {
        this.id = id;
    }

    public Long getRaffleId() {
        RafflePrizeId target = getId();
        return target == null ? null : target.getRaffleId();
    }

    public Long getPrizeId() {
        RafflePrizeId target = getId();
        return target == null ? null : target.getPrizeId();
    }

    public Raffle getRaffle() {
        return raffle;
    }

    public void setRaffle(Raffle raffle) {
        this.raffle = Objects.requireNonNull(raffle, "Raffle must not be null!");
    }

    void unsetRaffle() {
        this.raffle = null;
    }

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = Objects.requireNonNull(prize, "Prize must not be null!");
    }

    void unsetPrize() {
        this.prize = null;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
