package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The Prize DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
@Schema(
    name = "Prize",
    description = """
        In a context of a radio station, a prize is a reward that can be won by
        a raffle ticket after make a draw between all participants that had sent
        their as favorites songs as answers to a survey made by a radio station.
        """
)
public class PrizeDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 5757907879034020055L;

    @Schema(
        description = "The prize ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The prize title",
        example = "A trip to the Bahamas")
    private final String title;

    @Schema(description = "The prize description",
        example = """
            A trip to the Bahamas for two people with all expenses paid.
            
            The trip includes a 5-day stay at the Atlantis Paradise Island resort
            and a round trip flight from the winner's city to Nassau.""")
    private final String description;

    @Schema(
        description = "The prize monetary value",
        example = "1000.00")
    private final BigDecimal monetaryValue;

    /**
     * Constructs a new {@link PrizeDTO} with the given arguments.
     *
     * @param id            the prize ID
     * @param title         the prize title
     * @param description   the prize description
     * @param monetaryValue the prize monetary value
     */
    public PrizeDTO(Long id, String title, String description, BigDecimal monetaryValue) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.monetaryValue = monetaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrizeDTO other = (PrizeDTO) o;
        return Objects.equals(id, other.id) &&
            Objects.equals(title, other.title) &&
            Objects.equals(description, other.description) &&
            Objects.equals(monetaryValue, other.monetaryValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, monetaryValue);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, title='%s', monetaryValue=%s, description=%s",
            id, title, monetaryValue, description == null ? null : '\'' + description + '\'');
    }

    /**
     * Returns the prize ID.
     *
     * @return the prize ID
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Returns the prize title.
     *
     * @return the prize title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the prize description.
     *
     * @return the prize description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the prize monetary value.
     *
     * @return the prize monetary value
     */
    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }
}
