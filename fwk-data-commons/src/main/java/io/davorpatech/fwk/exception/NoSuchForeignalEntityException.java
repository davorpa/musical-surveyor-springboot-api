package io.davorpatech.fwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Exception raised when the detail of a certain model (entity, POJO, DTO...) has
 * not been found when searching for any of its identifiers, either by the primary
 * key or a natural one.
 *
 * <p>This will normally be used to indicate that a database record has not been
 * found.
 *
 * <p>This specialization is for those associations with a certain degree
 * of relationship within the graph of the set of entities that compose it.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchForeignalEntityException // NOSONAR
        extends NoSuchEntityException // NOSONAR
{
    private static final long serialVersionUID = -5126857789571772161L;

    /**
     * Construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type and identifier.
     *
     * @param domain the domain type
     * @param id     the identifier
     */
    public NoSuchForeignalEntityException(
            final String domain, final Serializable id)
    {
        super(domain, id);
    }

    /**
     * Construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param domain the domain type
     * @param id     the identifier
     * @param cause  the cause. It can be {@code null}.
     */
    public NoSuchForeignalEntityException(
            final String domain, final Serializable id, final @Nullable Throwable cause)
    {
        super(domain, id, cause);
    }

    /**
     * Construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type and identifier.
     *
     * @param type  the domain type
     * @param id    the identifier
     */
    public NoSuchForeignalEntityException(
            final Class<?> type, final Serializable id)
    {
        super(type, id);
    }

    /**
     * Construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param type   the domain type
     * @param id     the identifier
     * @param cause  the cause. It can be {@code null}.
     */
    public NoSuchForeignalEntityException(
            final Class<?> type, final Serializable id, final @Nullable Throwable cause)
    {
        super(type, id, cause);
    }

    /**
     * Lazy construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type and identifier.
     *
     * @param domain the domain type
     * @param id     the identifier
     */
    public static Supplier<? extends NoSuchForeignalEntityException> creater( // NOSONAR
            final String domain, final Serializable id)
    {
        return () -> new NoSuchForeignalEntityException(domain, id);
    }

    /**
     * Lazy construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type and identifier.
     *
     * @param type  the domain type
     * @param id    the identifier
     */
    public static Supplier<? extends NoSuchForeignalEntityException> creater( // NOSONAR
            final Class<?> type, final Serializable id)
    {
        return () -> new NoSuchForeignalEntityException(type, id);
    }

    /**
     * Lazy construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param domain the domain type
     * @param id     the identifier
     * @param cause  the root cause. It can be {@code null}.
     */
    public static Supplier<? extends NoSuchForeignalEntityException> creater( // NOSONAR
            final String domain, final Serializable id, final @Nullable Throwable cause)
    {
        return () -> new NoSuchForeignalEntityException(domain, id, cause);
    }

    /**
     * Lazy construct a {@code NoSuchForeignalEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param type   the domain type
     * @param id     the identifier
     * @param cause  the root cause. It can be {@code null}.
     */
    public static Supplier<? extends NoSuchForeignalEntityException> creater( // NOSONAR
            final Class<?> type, final Serializable id, final @Nullable Throwable cause)
    {
        return () -> new NoSuchForeignalEntityException(type, id, cause);
    }
}
