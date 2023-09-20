package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Input object for updating {@code Prize} instances.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the presentation layer and the services layer. It is
 * also used to validate the data sent to the services layer. It is a
 * good practice to use different DTOs for each one of service operations.
 * This way, the DTOs can be extended in the future without breaking the
 * service contract.
 *
 * <p>This is why the {@link CreatePrizeInput} and the {@link UpdatePrizeInput}
 * are different classes. They both represent the same data, but the
 * {@link UpdatePrizeInput} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code Prize}.
 * The {@code id} is not required to create a new {@code Prize} because
 * the server will generate a new {@code id} for the new {@code Prize}.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class UpdatePrizeInput extends BaseUpdateInputCmd<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -3030949110180296716L;

    @NotBlank
    @Size(max = PrizeConstants.TITLE_MAXLEN)
    private final String title;

    @Size(max = PrizeConstants.DESCRIPTION_MAXLEN)
    private final String description;

    @NotNull
    @Min(PrizeConstants.MONETARY_VALUE_MIN)
    private final BigDecimal monetaryValue;

    /**
     * Constructs a new {@link UpdatePrizeInput} with the given arguments.
     *
     * @param id            the prize ID
     * @param title         the prize title
     * @param description   the prize description (optional)
     * @param monetaryValue the prize monetary value
     */
    public UpdatePrizeInput(Long id, String title, String description, BigDecimal monetaryValue) {
        super(id);
        this.title = title;
        this.description = description;
        this.monetaryValue = monetaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdatePrizeInput other = (UpdatePrizeInput) o;
        return Objects.equals(title, other.title) &&
            Objects.equals(description, other.description) &&
            Objects.equals(monetaryValue, other.monetaryValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description, monetaryValue);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, title='%s', monetaryValue=%s, description='%s'",
            super.defineObjAttrs(), title, monetaryValue,
            description == null ? null : '\'' + description + '\'');
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
