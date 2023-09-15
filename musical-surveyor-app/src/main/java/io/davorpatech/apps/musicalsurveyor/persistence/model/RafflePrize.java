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

/**
 * The RafflePrize entity class.
 *
 * <p>A raffle prize is a prize that can be won by a raffle ticket after make a draw
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

    /**
     * Constructs a new {@code RafflePrize} instance without any properties set.
     */
    RafflePrize() {
        super();
    }

    /**
     * Constructs a new {@code RafflePrize} instance with the given properties set.
     *
     * @param raffleId the ID of the raffle
     * @param prizeId  the ID of the prize
     */
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

    /**
     * Sets the ID of the entity.
     *
     * <p>It is not recommended to use this method directly, as it is
     * intended to be used by the persistence layer.
     *
     * @param id the ID of the entity to set
     */
    public void setId(RafflePrizeId id) {
        this.id = id;
    }

    /**
     * Gets the ID of the raffle.
     *
     * @return the ID of the raffle
     */
    public Long getRaffleId() {
        RafflePrizeId target = getId();
        return target == null ? null : target.getRaffleId();
    }

    /**
     * Gets the ID of the prize.
     *
     * @return the ID of the prize
     */
    public Long getPrizeId() {
        RafflePrizeId target = getId();
        return target == null ? null : target.getPrizeId();
    }

    /**
     * Gets the raffle for this prize.
     *
     * @return
     */
    public Raffle getRaffle() {
        return raffle;
    }

    /**
     * Sets the raffle for this prize.
     *
     * @param raffle the raffle for this prize. It must not be {@code null}.
     */
    public void setRaffle(Raffle raffle) {
        this.raffle = Objects.requireNonNull(raffle, "Raffle must not be null!");
    }

    /**
     * Unsets the raffle for this prize.
     */
    void unsetRaffle() {
        this.raffle = null;
    }

    /**
     * Gets the prize for this prize.
     *
     * @return the prize for this prize.
     */
    public Prize getPrize() {
        return prize;
    }

    /**
     * Sets the prize for this prize.
     *
     * @param prize the prize for this prize. It must not be {@code null}.
     */
    public void setPrize(Prize prize) {
        this.prize = Objects.requireNonNull(prize, "Prize must not be null!");
    }

    /**
     * Unsets the prize for this prize.
     */
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
     * @param winnerTicket the winner raffle ticket for this prize, never {@code null}
     */
    public void setWinnerTicket(RaffleTicket winnerTicket) {
        this.winnerTicket = Objects.requireNonNull(
            winnerTicket, "RaffleTicket winnerTicket must not be null!");
    }

    void unsetWinnerTicket() {
        this.winnerTicket = null;
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
