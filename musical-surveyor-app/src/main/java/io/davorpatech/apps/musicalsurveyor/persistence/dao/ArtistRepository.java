package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
