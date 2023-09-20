package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code Raffle} repository interface.
 *
 * <p>As a repository, it provides access to the {@code Raffle} entities
 * in the database, including operations for saving, deleting,
 * and finding {@code Raffle} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see Raffle
 */
@Repository
@Transactional(readOnly = true)
public interface RaffleRepository extends JpaRepository<Raffle, Long>
{

}
