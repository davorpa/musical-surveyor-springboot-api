package io.davorpatech.apps.musicalsurveyor.web.model.colors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.davorpatech.apps.musicalsurveyor.domain.colors.ColorConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Represents the HTTP request body to create a new {@code Color}.
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
 * <p>This is why the {@link CreateColorRequest} and the
 * {@link UpdateColorRequest} are different classes. They both
 * represent the same data, but the {@link UpdateColorRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code Color}. The {@code id} is
 * not required to create a new {@code Color} because the server
 * will generate a new {@code id} for the new {@code Color}.
 */
@Schema(
    name = "CreateColorRequest",
    description = "Represents the HTTP request body to create a new Color."
)
public class CreateColorRequest extends BaseValueObject
{
    @Serial
    private static final long serialVersionUID = -7548605579321449801L;

    @Schema(
        description = """
            The color code.
            
            It have to follow the hexadecimal format, e.g. #FF0000 for red; or also color names, e.g. red.""",
        example = "red")
    @NotBlank
    @Size(max = ColorConstants.CODE_MAXLEN)
    @Pattern(regexp = ColorConstants.CODE_REGEX)
    private final String code;

    /**
     * Creates a new {@link CreateColorRequest} instance with the given arguments.
     *
     * @param code the color code
     */
    @JsonCreator
    public CreateColorRequest(String code) {
        super();
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateColorRequest other = (CreateColorRequest) o;
        return Objects.equals(code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("code='%s'", code);
    }

    /**
     * Returns the color code.
     *
     * @return the color code
     */
    public String getCode() {
        return code;
    }
}
