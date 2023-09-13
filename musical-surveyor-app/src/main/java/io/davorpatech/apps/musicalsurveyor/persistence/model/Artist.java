package io.davorpatech.apps.musicalsurveyor.persistence.model;

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

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@EntityListeners({
        AuditingEntityListener.class
})
@Entity
@Table(
        name = "ARTIST"
)
public class Artist extends BaseEntity<Long> implements AuditAccessor{

    private static final long serialVersionUID = 6983478586962494353L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(name = "biography", length = 2048, nullable = true)
    @Size(max = 2048)
    private String biography;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    @OrderBy("title ASC, year ASC")
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<Song> getSongs() {
        return Set.copyOf(songs);
    }

    public void setSongs(Set<Song> songs) {
        this.songs = Objects.requireNonNull(songs, "songs must not be null!");
    }

    public void addSong(
            final Song song) {
        Objects.requireNonNull(song, "song to add must not be null!");
        songs.add(song);
        song.setArtist(this);
    }

    public void removeSong(
            final Song song) {
        Objects.requireNonNull(song, "asistencia to remove must not be null!");
        songs.remove(song);
        song.unsetArtist();
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
