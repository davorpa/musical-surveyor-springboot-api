package io.davorpatech.apps.musicalsurveyor.domain.artist;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Input object for updating {@code Artist} instances.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the presentation layer and the services layer. It is
 * also used to validate the data sent to the services layer. It is a
 * good practice to use different DTOs for each one of service operations.
 * This way, the DTOs can be extended in the future without breaking the
 * service contract.
 *
 * <p>This is why the {@link CreateArtistInput} and the {@link UpdateArtistInput}
 * are different classes. They both represent the same data, but the
 * {@link UpdateArtistInput} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code Artist}.
 * The {@code id} is not required to create a new {@code Artist} because
 * the server will generate a new {@code id} for the new {@code Artist}.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class UpdateArtistInput extends BaseUpdateInputCmd<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 4056172433555198885L;

    @NotBlank
    @Size(max = ArtistConstants.NAME_MAXLEN)
    private final String name;

    @Size(max = ArtistConstants.BIOGRAPHY_MAXLEN)
    private final String biography;

    /**
     * Constructs a new {@link UpdateArtistInput} with the given arguments.
     *
     * @param id         the artist id
     * @param name       the artist name
     * @param biography  the artist biography
     */
    public UpdateArtistInput(Long id, String name, String biography) {
        super(id);
        this.name = name;
        this.biography = biography;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateArtistInput other = (UpdateArtistInput) o;
        return Objects.equals(name, other.name) &&
            Objects.equals(biography, other.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, biography);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, name='%s', biography='%s'",
            super.defineObjAttrs(), name, biography);
    }

    /**
     * Returns the artist name.
     *
     * @return the artist name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the artist biography.
     *
     * @return the artist biography
     */
    public String getBiography() {
        return biography;
    }
}
