package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.RaffleTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaffleTicketRepository extends JpaRepository<RaffleTicket, Long>
{

}
