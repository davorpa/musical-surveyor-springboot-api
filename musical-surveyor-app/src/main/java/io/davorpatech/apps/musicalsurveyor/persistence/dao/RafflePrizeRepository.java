package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.RafflePrize;
import io.davorpatech.apps.musicalsurveyor.persistence.model.RafflePrizeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RafflePrizeRepository extends JpaRepository<RafflePrize, RafflePrizeId>
{

}
