package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@EntityListeners({
        AuditingEntityListener.class
})
@Entity
@Table(name = "SONG")
public class Song extends BaseEntity<Long> implements AuditAccessor {

    private static final long serialVersionUID = -8577673241957398795L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    @NotBlank
    @Size(max = 255)
    private String title;

    @Column(name = "`year`", nullable = true)
    @Min(0)
    private Integer year;

    @Column(name = "duration", nullable = true)
    @Min(0)
    private Integer duration;

    @Column(name = "genre", length = 50, nullable = true)
    @Size(max = 50)
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

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, artist_id=%s, title='%s', year=%s, duration=%s, genre='%s'",
                super.defineObjAttrs(), getArtistId(), title, year, duration, genre);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = Objects.requireNonNull(artist, "Artist must not be null!");
    }

    void unsetArtist() {
        this.artist = null;
    }

    public Long getArtistId() {
        Artist target = getArtist();
        return target == null ? null : target.getId();
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
