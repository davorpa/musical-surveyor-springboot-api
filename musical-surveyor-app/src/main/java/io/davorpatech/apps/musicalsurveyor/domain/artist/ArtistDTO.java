package io.davorpatech.apps.musicalsurveyor.domain.artist;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Objects;

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
@Schema(
    name = "Artist",
    description = """
        In a context of a radio station, an artist is a person or group of
        people who create music.
        
        An artist can have many songs and a song can only have one artist.
        """
)
public class ArtistDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 3537265284481948367L;

    @Schema(
        description = "The artist ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The artist name",
        example = "The Beatles")
    private final String name;

    @Schema(description = "The artist biography",
        example = """
            The Beatles were an English rock band formed in Liverpool in 1960.
            
            With a line-up comprising John Lennon, Paul McCartney, George Harrison and Ringo Starr,
            they are regarded as the most influential band of all time.""")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistDTO other = (ArtistDTO) o;
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
        return String.format("id=%s, name='%s', biography='%s'",
            id, name, biography);
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
}
