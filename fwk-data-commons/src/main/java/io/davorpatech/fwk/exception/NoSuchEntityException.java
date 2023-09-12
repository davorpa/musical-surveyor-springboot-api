package io.davorpatech.fwk.exception;

import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.function.Supplier;

import static java.lang.String.format;

/**
 * Exception raised when the detail of a certain model (entity, POJO, DTO...) has
 * not been found when searching for any of its identifiers, either by the primary
 * key or a natural one.
 *
 * <p>This will normally be used to indicate that a database record has not been
 * found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchEntityException // NOSONAR
        extends PreconditionalException // NOSONAR
        implements Identifiable<Serializable>, ErrorDomain // NOSONAR
{
    private static final long serialVersionUID = -5126857789571772161L;

    private final String domain;

    private final Serializable id;

    /**
     * Construct a {@code NoSuchEntityException} with the specified
     * domain type and identifier.
     *
     * @param domain the domain type
     * @param id     the identifier
     */
    public NoSuchEntityException(
            final String domain, final Serializable id)
    {
        super(format("The `%s` identified by `%s` don't exist.", domain, id));
        this.domain = domain;
        this.id = id;
    }

    /**
     * Construct a {@code NoSuchEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param domain the domain type
     * @param id     the identifier
     * @param cause  the cause. It can be {@code null}
     */
    public NoSuchEntityException(
            final String domain, final Serializable id, final @Nullable Throwable cause)
    {
        this(domain, id);
        initCause(cause);
    }

    /**
     * Construct a {@code NoSuchEntityException} with the specified
     * domain type and identifier.
     *
     * @param type  the domain type
     * @param id    the identifier
     */
    public NoSuchEntityException(
            final Class<?> type, final Serializable id)
    {
        this(type.getTypeName(), id);
    }

    /**
     * Construct a {@code NoSuchEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param type   the domain type
     * @param id     the identifier
     * @param cause  the cause. It can be {@code null}
     */
    public NoSuchEntityException(
            final Class<?> type, final Serializable id, final @Nullable Throwable cause)
    {
        this(type, id);
        initCause(cause);
    }

    /**
     * Lazy construct a {@code NoSuchEntityException} with the specified
     * domain type and identifier.
     *
     * @param domain the domain type
     * @param id     the identifier
     */
    public static Supplier<? extends NoSuchEntityException> creater( // NOSONAR
            final String domain, final Serializable id)
    {
        return () -> new NoSuchEntityException(domain, id);
    }

    /**
     * Lazy construct a {@code NoSuchEntityException} with the specified
     * domain type and identifier.
     *
     * @param type  the domain type
     * @param id    the identifier
     */
    public static Supplier<? extends NoSuchEntityException> creater( // NOSONAR
            final Class<?> type, final Serializable id)
    {
        return () -> new NoSuchEntityException(type, id);
    }

    /**
     * Lazy construct a {@code NoSuchEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param domain the domain type
     * @param id     the identifier
     * @param cause  the root cause. It can be {@code null}
     */
    public static Supplier<? extends NoSuchEntityException> creater( // NOSONAR
            final String domain, final Serializable id, final @Nullable Throwable cause)
    {
        return () -> new NoSuchEntityException(domain, id, cause);
    }

    /**
     * Lazy construct a {@code NoSuchEntityException} with the specified
     * domain type, identifier and root cause.
     *
     * @param type   the domain type
     * @param id     the identifier
     * @param cause  the root cause. It can be {@code null}
     */
    public static Supplier<? extends NoSuchEntityException> creater( // NOSONAR
            final Class<?> type, final Serializable id, final @Nullable Throwable cause)
    {
        return () -> new NoSuchEntityException(type, id, cause);
    }

    @Override
    public String getDomain()
    {
        return domain;
    }

    @Override
    public Serializable getId()
    {
        return id;
    }
}
