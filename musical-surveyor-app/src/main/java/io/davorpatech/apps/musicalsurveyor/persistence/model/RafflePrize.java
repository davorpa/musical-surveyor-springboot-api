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

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
        name = "winner_ticket_id",
        nullable = true,
        foreignKey = @ForeignKey(name = "FK_raffle_prize_winner_ticket_id"))
    private RaffleTicket winnerTicket;

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
        return String.format("%s, winnerTicketId=%s",
            super.defineObjAttrs(), getWinnerTicketId());
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

    /**
     * Gets the winner raffle ticket for this prize.
     *
     * <p>If the prize has not been delivered to the winner, this method returns {@code null}.
     *
     * @return the winner raffle ticket for this prize.
     */
    public RaffleTicket getWinnerTicket() {
        return winnerTicket;
    }

    /**
     * Sets the winner raffle ticket for this prize.
     *
     * @param winnerTicket the winner raffle ticket for this prize.
     */
    public void setWinnerTicket(RaffleTicket winnerTicket) {
        this.winnerTicket = winnerTicket;
    }

    /**
     * Gets the winner raffle ticket ID for this prize.
     *
     * <p>If the prize has not been delivered to the winner, this method returns {@code null}.
     *
     * @return the winner raffle ticket ID for this prize.
     */
    public Long getWinnerTicketId() {
        RaffleTicket target = getWinnerTicket();
        return target == null ? null : target.getId();
    }

    /**
     * Checks if the prize has been delivered to the winner, thus is,
     * if some winner raffle ticket is linked to this prize
     * (<code>{@link #getWinnerTicket()} != null</code>).
     *
     * @return {@code true} if the prize has been delivered to the winner,
     *         {@code false} otherwise.
     * @see #getWinnerTicket()
     */
    public boolean isDelivered() {
        return getWinnerTicket() != null;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
