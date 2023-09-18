package io.davorpatech.apps.musicalsurveyor.domain.colors;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * Input object for updating {@code Color} instances.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the presentation layer and the services layer. It is
 * also used to validate the data sent to the services layer. It is a
 * good practice to use different DTOs for each one of service operations.
 * This way, the DTOs can be extended in the future without breaking the
 * service contract.
 *
 * <p>This is why the {@link CreateColorInput} and the {@link UpdateColorInput}
 * are different classes. They both represent the same data, but the
 * {@link UpdateColorInput} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code Color}.
 * The {@code id} is not required to create a new {@code Color} because
 * the server will generate a new {@code id} for the new {@code Color}.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class UpdateColorInput extends BaseUpdateInputCmd<Long> // NOSONAR
{
    @NotBlank
    @Size(max = ColorConstants.CODE_MAXLEN)
    @Pattern(regexp = ColorConstants.CODE_REGEX)
    private final String code;

    /**
     * Constructs a new {@link UpdateColorInput} with the given arguments.
     *
     * @param id the color id
     * @param code the color code
     */
    public UpdateColorInput(Long id, String code) {
        super(id);
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateColorInput other = (UpdateColorInput) o;
        return Objects.equals(code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, code='%s'", super.defineObjAttrs(), code);
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
