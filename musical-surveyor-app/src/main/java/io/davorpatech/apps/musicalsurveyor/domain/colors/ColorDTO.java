package io.davorpatech.apps.musicalsurveyor.domain.colors;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Objects;

/**
 * The Color DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
@Schema(
    name = "Color",
    description = """
        A color is a visual mask that can be applied to raffle tickets.
        
        Colors are used as part of to differentiate raffle tickets from each other.
        
        It code format is validated as hexadecimal values, e.g. #FF0000 for red; or also as color names, e.g. red."""
)
public class ColorDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 5341086927775860849L;

    @Schema(
        description = "The color internal ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The color code",
        example = "red")
    private final String code;

    /**
     * Constructs a new {@link ColorDTO} with the given arguments.
     *
     * @param id the color ID
     * @param code the color code
     */
    public ColorDTO(Long id, String code) {
        super();
        this.id = id;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorDTO other = (ColorDTO) o;
        return Objects.equals(id, other.id) &&
            Objects.equals(code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, code='%s'", id, code);
    }

    /**
     * Returns the color ID.
     *
     * @return the color ID
     */
    @Override
    public Long getId() {
        return id;
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
