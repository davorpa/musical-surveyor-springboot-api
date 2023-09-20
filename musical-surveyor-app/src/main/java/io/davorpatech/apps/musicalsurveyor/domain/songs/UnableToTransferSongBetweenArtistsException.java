package io.davorpatech.apps.musicalsurveyor.domain.songs;

import io.davorpatech.fwk.exception.PreconditionalException;
import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Exception raised when we try to change the song artist to another, thus is
 * song transfering between artists is not allowed.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class UnableToTransferSongBetweenArtistsException extends PreconditionalException // NOSONAR
    implements Identifiable<Serializable>, ErrorDomain, AdditionalArgumentsPopulator // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 3994731842069594082L;

    private final Serializable id;

    private final Long fromArtistId;

    private final Long toArtistId;

    /**
     * Construct a {@code ImmutableSongArtistException} with the specified arguments.
     *
     * @param id           the song ID we tried to transfer
     * @param fromArtistId the identifier of the current artist owner of the song
     *                     we tried to transfer
     * @param toArtistId   the identifier of the artist we tried to transfer the
     *                     song to
     */
    public UnableToTransferSongBetweenArtistsException(
            Serializable id, Long fromArtistId, Long toArtistId) {
        super(String.format(
            "Unable to transfer the song identified by `%s` from artist `%s` to `%s`.",
            id, fromArtistId, toArtistId));
        this.id = id;
        this.fromArtistId = fromArtistId;
        this.toArtistId = toArtistId;
    }

    @Override
    public String getDomain() {
        return SongConstants.DOMAIN_NAME;
    }

    /**
     * Returns the song ID where the business rule has been violated.
     *
     * @return the identifier of the song
     */
    @Override
    public Serializable getId() {
        return id;
    }

    /**
     * Returns the affected field of the domain object.
     *
     * <p>In this case, the affected field is the {@code artist.id}.
     *
     * @return the affected field of the domain object
     */
    public String getFieldName() {
        return "artist.id";
    }

    /**
     * Returns the identifier of the current artist owner of the song
     * we tried to transfer
     *
     * @return the identifier of the current artist owner of the song
     *         we tried to transfer
     */
    public Long getFromArtistId() {
        return fromArtistId;
    }

    /**
     * Returns the rejected value, in this case the identifier of the artist
     * we tried to transfer the song to.
     *
     * @return the identifier of the artist we tried to transfer the song to
     */
    public Long getToArtistId() {
        return toArtistId;
    }

    @Override
    public void populate(
            final Environment environment,
            final Map<String, Object> attributes)
    {
        attributes.put("field", getFieldName());
        attributes.put("fromArtist", getFromArtistId());
        attributes.put("toArtist", getToArtistId());
    }
}
