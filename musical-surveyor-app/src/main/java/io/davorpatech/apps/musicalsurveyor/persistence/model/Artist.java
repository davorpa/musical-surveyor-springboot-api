package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.artist.ArtistConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The Artist entity class.
 *
 * <p>In a context of a radio station, an artist is a person or group of
 * people who create music.
 *
 * <p>An artist can have many songs and a song can only have one artist.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = ArtistConstants.DOMAIN_NAME)
@Table(name = "ARTIST")
public class Artist extends BaseEntity<Long> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 6983478586962494353L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "name", length = ArtistConstants.NAME_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = ArtistConstants.NAME_MAXLEN)
    private String name;

    @Column(name = "biography", length = ArtistConstants.BIOGRAPHY_MAXLEN, nullable = true)
    @Size(max = ArtistConstants.BIOGRAPHY_MAXLEN)
    private String biography;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    @OrderBy("title ASC, releaseYear ASC")
    private Set<@Valid Song> songs = new LinkedHashSet<>();

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, name='%s', songs=%s",
                super.defineObjAttrs(), name, songs.size());
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the entity.
     *
     * <p>It is not recommended to use this method directly, as it is
     * intended to be used by the persistence layer.
     *
     * @param id the ID of the entity to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the musical artist.
     *
     * @return the name of the musical artist
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the musical artist.
     *
     * @param name the name of the musical artist to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the biography of the musical artist.
     *
     * @return the biography of the musical artist
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the biography of the musical artist.
     *
     * @param biography the biography of the musical artist to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Gets the songs collection owned by the musical artist.
     *
     * <p>The returned collection is unmodifiable copy of the original.
     *
     * @return the songs of the musical artist
     */
    public Set<Song> getSongs() {
        return Set.copyOf(songs); // ensure immutability
    }

    /**
     * Sets the songs collection owned by the musical artist.
     *
     * @param songs the songs of the musical artist to set, must not be {@code null}.
     */
    public void setSongs(Set<Song> songs) {
        this.songs = Objects.requireNonNull(songs, "songs must not be null!");
    }

    /**
     * Adds a new song to the musical artist repertoire.
     *
     * <p>As part of a bidirectional relationship, this method also sets the
     * artist of that song to this artist.
     *
     * @param song the song to add, must not be {@code null}.
     */
    public void addSong(Song song) {
        Objects.requireNonNull(song, "song to add must not be null!");
        songs.add(song);
        song.setArtist(this);
    }

    /**
     * Removes a song from those owned by the musical artist repertoire.
     *
     * <p>As part of a bidirectional relationship, this method also unsets the
     * artist of that song.
     *
     * @param song the song to remove, must not be {@code null}.
     */
    public void removeSong(Song song) {
        Objects.requireNonNull(song, "asistencia to remove must not be null!");
        songs.remove(song);
        song.unsetArtist();
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
