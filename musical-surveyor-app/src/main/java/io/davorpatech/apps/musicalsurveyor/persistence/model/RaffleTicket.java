package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

@Entity
@Table(
    name = "RAFFLE_TICKET",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_raffle_ticket_number",
            columnNames = {"number"}
        )
    }
)
public class RaffleTicket {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "raffle_ticket", length = 40, nullable = false)
    @NotBlank
    @Size(max = 40)
    private String number;

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
}
