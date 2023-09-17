package io.davorpatech.apps.musicalsurveyor.web.model.artist;

import io.davorpatech.apps.musicalsurveyor.domain.artist.ArtistConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Represents the HTTP request body to update an existing {@code Artist}.
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
 * <p>This is why the {@link CreateArtistRequest} and the
 * {@link UpdateArtistRequest} are different classes. They both
 * represent the same data, but the {@link UpdateArtistRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code Artist}. The {@code id} is
 * not required to create a new {@code Artist} because the server
 * will generate a new {@code id} for the new {@code Artist}.
 */
public class UpdateArtistRequest extends BaseValueObject implements Identifiable<Long>
{
    @Serial
    private static final long serialVersionUID = -5790988780557771252L;

    private final Long id;

    @NotBlank
    @Size(max = ArtistConstants.NAME_MAXLEN)
    private final String name;

    @Size(max = ArtistConstants.BIOGRAPHY_MAXLEN)
    private final String biography;

    /**
     * Creates a new {@link UpdateArtistRequest} instance with the given arguments.
     *
     * @param id        the artist ID
     * @param name      the artist name
     * @param biography the artist biography
     */
    public UpdateArtistRequest(Long id, String name, String biography) {
        super();
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateArtistRequest other = (UpdateArtistRequest) o;
        return Objects.equals(id, other.id) &&
            Objects.equals(name, other.name) &&
            Objects.equals(biography, other.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, biography);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, name='%s', biography='%s'", id, name, biography);
    }

    @Override
    public Long getId() {
        return id;
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
