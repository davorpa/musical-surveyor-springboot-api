package io.davorpatech.apps.musicalsurveyor.persistence.dao;

import io.davorpatech.apps.musicalsurveyor.persistence.model.RadioListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code RadioListener} repository interface.
 *
 * <p>As a repository, it provides access to the {@code RadioListener}
 * entities in the database, including operations for saving, deleting,
 * and finding {@code RadioListener} entities.
 *
 * <p>It extends the {@link JpaRepository} interface, which provides
 * access to the basic CRUD operations, plus JPA-specific operations.
 *
 * <p>It is annotated with {@link Repository}, which is a Spring
 * stereotype annotation that indicates that the decorated class
 * is a repository.
 *
 * @see JpaRepository
 * @see RadioListener
 */
@Repository
@Transactional(readOnly = true)
public interface RadioListenerRepository extends JpaRepository<RadioListener, Long>
{
    /**
     * Returns whether there are any {@code RadioListener} entities stored in
     * the repository.
     *
     * @return {@code true} if there are any {@code RadioListener} entities,
     *         {@code false} otherwise
     *
     * @see #count()
     */
    default boolean isEmpty() {
        return count() == 0L;
    }
}
