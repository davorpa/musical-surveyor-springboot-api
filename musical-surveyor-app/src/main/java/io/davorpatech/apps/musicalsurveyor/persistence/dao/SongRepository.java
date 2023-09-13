package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
