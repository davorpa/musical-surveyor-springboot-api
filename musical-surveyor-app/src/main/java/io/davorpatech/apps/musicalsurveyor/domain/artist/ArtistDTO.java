package io.davorpatech.apps.musicalsurveyor.domain.artist;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import java.io.Serial;

/**
 * The Artist DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
public class ArtistDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 3537265284481948367L;

    private final Long id;

    private final String name;

    private final String biography;

    /**
     * Constructs a new {@link ArtistDTO} with the given arguments.
     *
     * @param id the artist ID
     * @param name the artist name
     * @param biography the artist biography
     */
    public ArtistDTO(Long id, String name, String biography) {
        super();
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    /**
     * Returns the artist ID.
     *
     * @return the artist ID
     */
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

    /**
     * Returns a string representation of the attributes of this
     * {@link ArtistDTO}.
     *
     * @return a string representation of the attributes of this
     *         {@link ArtistDTO}
     */
    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, name='%s', biography='%s'",
                id, name, biography);
    }
}
