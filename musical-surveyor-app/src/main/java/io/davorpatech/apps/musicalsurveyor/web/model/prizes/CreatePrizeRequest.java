package io.davorpatech.apps.musicalsurveyor.web.model.prizes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.PrizeConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents the HTTP request body to create a new {@code Prize}.
 *
 * <p>Request DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the client and the server. It is also used to validate
 * the data sent to the server. It is a good practice to use different
 * DTOs for the request and the response. This way, the response DTO
 * can be extended in the future without breaking the client. This
 * is not the case for the request DTO, which should be kept as simple
 * as possible.
 *
 * <p>This is why the {@link CreatePrizeRequest} and the
 * {@link UpdatePrizeRequest} are different classes. They both
 * represent the same data, but the {@link UpdatePrizeRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code Prize}. The {@code id} is
 * not required to create a new {@code Prize} because the server
 * will generate a new {@code id} for the new {@code Prize}.
 */
@Schema(
    name = "CreatePrizeRequest",
    description = "Represents the HTTP request body to create a new Prize."
)
public class CreatePrizeRequest extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -4415811645241753330L;

    @Schema(
        description = "The prize name",
        example = "A trip to the Bahamas")
    @NotBlank
    @Size(max = PrizeConstants.TITLE_MAXLEN)
    private final String title;

    @Schema(description = "The prize description",
        example = """
            A trip to the Bahamas for two people with all expenses paid.
            
            The trip includes a 5-day stay at the Atlantis Paradise Island resort
            and a round trip flight from the winner's city to Nassau.""")
    @Size(max = PrizeConstants.DESCRIPTION_MAXLEN)
    private final String description;

    @Schema(
        description = "The prize monetary value",
        example = "1000.00")
    @NotNull
    @Min(PrizeConstants.MONETARY_VALUE_MIN)
    private final BigDecimal monetaryValue;

    /**
     * Constructs a new {@link CreatePrizeRequest} with the given arguments.
     *
     * @param title         the prize title
     * @param description   the prize description (optional)
     * @param monetaryValue the prize monetary value
     */
    @JsonCreator
    public CreatePrizeRequest(String title, String description, BigDecimal monetaryValue) {
        super();
        this.title = title;
        this.description = description;
        this.monetaryValue = monetaryValue;
    }

    @Override
    public String toString() {
        return "CreatePrizeRequest{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", monetaryValue=" + monetaryValue +
            '}';
    }

    /*
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, monetaryValue);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("title='%s', monetaryValue=%s, description=%s",
            title, monetaryValue, description == null ? null : '\'' + description + '\'');
    }

    /**
     * Returns the prize title.
     *
     * @return the prize title
     */

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

}
