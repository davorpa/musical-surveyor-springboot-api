package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.RaffleTicketConstants;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serial;

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

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, number='%s', color=%s",
            super.defineObjAttrs(), number, color);
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
}
