package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.RaffleTicketConstants;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.util.Optional;

@Entity(name = RaffleTicketConstants.DOMAIN_NAME)
@Table(
    name = "RAFFLE_TICKET",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_raffle_ticket_number_color",
            columnNames = {"number", "color_id"}
        )
    }
)
public class RaffleTicket extends BaseEntity<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 5222976463579443925L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "`number`", length = RaffleTicketConstants.NUMBER_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = RaffleTicketConstants.NUMBER_MAXLEN)
    @Pattern(regexp = RaffleTicketConstants.NUMBER_REGEX)
    private String number;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "color_id",
        nullable = false,
        updatable = false,
        foreignKey = @ForeignKey(name = "FK_raffle_ticket_color_id")
    )
    @Valid
    private Color color;

    @OneToOne(mappedBy = "winnerTicket", optional = true,
        cascade = CascadeType.ALL, orphanRemoval = true,
        fetch = FetchType.EAGER)
    private RafflePrize rafflePrize;

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, number='%s', colorId=%s, colorCode='%s', wonPrizeId=%s",
            super.defineObjAttrs(), number, getColorId(), getColorCode(), getRaffledPrizeId());
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getColorCode() {
        Color target = getColor();
        return target == null ? null : color.getCode();
    }

    public Long getColorId() {
        Color target = getColor();
        return target == null ? null : color.getId();
    }

    /**
     * Returns the raffle prize associated with this raffle ticket
     * if is a winner ticket after make a raffle.
     *
     * @return the raffle prize associated with this raffle ticket, if any.
     */
    public RafflePrize getRafflePrize() {
        return rafflePrize;
    }

    /**
     * Sets the raffle prize associated with this raffle ticket.
     *
     * <p><b>NOTE</b>: Since this is the owning side of the @{@link OneToOne}
     * relationship, is perfect to use this method as linker between one raffle
     * prize and its winner raffle ticket after make a raffle.
     *
     * @param rafflePrize the raffle prize associated with this raffle ticket, if any.
     */
    public void setRafflePrize(RafflePrize rafflePrize) {
        if (rafflePrize == null) { // unlink bidirectional relationship
            if (this.rafflePrize != null) {
                // dispose previous references
                this.rafflePrize.setWinnerTicket(null);
            }
        } else { // link bidirectional relationship
            rafflePrize.setWinnerTicket(this);
        }
        this.rafflePrize = rafflePrize;
    }

    /**
     * Gets the winning raffled prize ID, if any, for this raffle ticket.
     * Otherwise, returns {@code null}.
     *
     * @return the winning raffled prize ID, if any, for this raffle ticket.
     */
    public Long getRaffledPrizeId() {
        RafflePrize target = getRafflePrize();
        return Optional.ofNullable(target)
            // navigation using RafflePrize.RafflePrizeId
            .map(RafflePrize::getId)
            .map(RafflePrizeId::getPrizeId)
            // or else using RafflePrize.Prize
            .orElseGet(() -> Optional.ofNullable(target)
                .map(RafflePrize::getPrize)
                .map(Prize::getId)
                .orElse(null));
    }

    /**
     * Returns {@code true} if this raffle ticket is a winner ticket after make a
     * raffle. Otherwise, returns {@code false}.
     *
     * @return {@code true} if this raffle ticket is a winner ticket after make a raffle.
     * @see #getRafflePrize()
     */
    public boolean isWinnerTicket() {
        return getRafflePrize() != null;
    }
}
