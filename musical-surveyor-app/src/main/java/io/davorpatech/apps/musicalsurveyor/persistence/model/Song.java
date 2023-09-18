package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.songs.SongConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The Song entity class.
 *
 * <p>In a context of a radio station, a song is a piece of music that can be
 * played on the radio. It is usually a single track from an album.
 *
 * <p>A song can only have one artist and an artist can have many songs.
 *
 * <p>Audience members of a radio station can respond to a survey by selecting
 * songs that they like and, a song can be selected by many audience members
 * as the way to know their musical preferences. This many-to-many
 * relationship has its owning side at the {@link SurveyParticipation} entity.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = SongConstants.DOMAIN_NAME)
@Table(name = "SONG")
public class Song extends BaseEntity<Long> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -8577673241957398795L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "title", length = SongConstants.TITLE_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = SongConstants.TITLE_MAXLEN)
    private String title;

    @Column(name = "`year`", nullable = true)
    @Min(SongConstants.YEAR_MIN)
    private Integer year;

    @Column(name = "duration", nullable = true)
    @Min(SongConstants.DURATION_MIN)
    private Integer duration;

    @Column(name = "genre", length = SongConstants.GENRE_MAXLEN, nullable = true)
    @Size(max = SongConstants.GENRE_MAXLEN)
    private String genre;

    @Embedded
    private final Audit audit = new Audit();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "artist_id",
        nullable = false,
        updatable = false,
        foreignKey = @ForeignKey(name = "FK_song_artist_id"))
    @Valid
    private Artist artist;

    @ManyToMany(mappedBy = "responses", fetch = FetchType.LAZY)
    @OrderBy("participatedAt ASC")
    Set<@Valid SurveyParticipation> participations = new LinkedHashSet<>(); // NOSONAR

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "%s, artist_id=%s, title='%s', year=%s, duration=%s, genre='%s', participations=%s",
            super.defineObjAttrs(), getArtistId(), title, year, duration, genre,
            participations.size());
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
     * Gets the title of the song.
     *
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the song.
     *
     * @param title the title of the song to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the year of the song.
     *
     * @return the year of the song
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the year of the song.
     *
     * @param year the year of the song to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets the duration of the song in seconds.
     *
     * <p>If set, it is always a positive integer.
     *
     * @return the duration of the song in seconds
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the song in seconds.
     *
     * <p>If set, it must be a positive integer.
     *
     * @param duration the duration of the song in seconds to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets the musical genre of the song.
     *
     * <p>It is a free text field such as {@code Rock}, {@code Pop}, {@code Jazz}, etc.
     *
     * @return the genre of the song
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the musical genre of the song.
     *
     * <p>If set, it is a free text field such as {@code Rock}, {@code Pop}, {@code Jazz}, etc.
     *
     * @param genre the genre of the song to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the artist of the song.
     *
     * @return the artist of the song
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the artist of the song.
     *
     * @param artist the artist of the song to set, never {@code null}
     */
    public void setArtist(Artist artist) {
        this.artist = Objects.requireNonNull(artist, "Artist must not be null!");
    }

    /**
     * Unsets the artist of the song.
     */
    void unsetArtist() {
        this.artist = null;
    }

    /**
     * Gets the ID of the artist of the song.
     *
     * @return the ID of the artist of the song
     */
    public Long getArtistId() {
        Artist target = getArtist();
        return target == null ? null : target.getId();
    }

    /**
     * Gets the survey participations where a song has been selected as a
     * survey response.
     *
     * @return the survey participations where a song has been selected as a response
     */
    public Set<SurveyParticipation> getParticipations() {
        return Set.copyOf(participations); // ensure immutability
    }

    /**
     * Sets the survey participations where a song has been selected as a
     * survey response.
     *
     * @param participations the survey participations where a song has been
     *                       selected as a response, never {@code null}
     */
    public void setParticipations(Set<SurveyParticipation> participations) {
        this.participations = Objects.requireNonNull(
            participations, "participations must not be null!");
    }

    /**
     * Adds a survey participation where a song has been selected as a
     * survey response.
     *
     * @param participation the survey participation where a song has been
     *                      selected as a response, never {@code null}
     */
    public void addParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to add must not be null!");
        participations.add(participation); // register in this side
        participation.responses.add(this); // register in the other side
    }

    /**
     * Removes a survey participation where a song has been selected as a
     * survey response.
     *
     * @param participation the survey participation where a song has been
     *                      selected as a response, never {@code null}
     */
    public void removeParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to remove must not be null!");
        participations.remove(participation); // unregister in this side
        participation.responses.remove(this); // unregister in the other side
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
