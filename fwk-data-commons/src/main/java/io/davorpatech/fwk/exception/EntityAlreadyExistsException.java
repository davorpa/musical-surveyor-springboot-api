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
 * Exception that is thrown when the detail of a specific model object
 * (entity, POJO, DTO...) already exists when searching for any of its
 * identifiers.
 *
 * <p>This will normally be used to indicate that a database Unique or
 * Primary Key has been violated.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException // NOSONAR
        extends PreconditionalException // NOSONAR
        implements Identifiable<Serializable>, ErrorDomain // NOSONAR
{
    private static final long serialVersionUID = 4187554634461423264L;

    private final String domain;

    private final Serializable id;

    /**
     * Construct a {@code EntityAlreadyExistsException} with the specified
     * domain type and identifier.
     *
     * @param domain the domain type
     * @param id     the identifier
     */
    public EntityAlreadyExistsException(
            final String domain, final Serializable id)
    {
        super(format("The `%s` identified by `%s` already exist.", domain, id));
        this.domain = domain;
        this.id = id;
    }

    /**
     * Construct a {@code EntityAlreadyExistsException} with the specified
     * domain type, identifier and root cause.
     *
     * @param domain the domain type
     * @param id     the identifier
     * @param cause  the cause. It can be {@code null}
     */
    public EntityAlreadyExistsException(
            final String domain, final Serializable id, final @Nullable Throwable cause)
    {
        this(domain, id);
        initCause(cause);
    }

    /**
     * Construct a {@code EntityAlreadyExistsException} with the specified
     * domain type and identifier.
     *
     * @param type  the domain type
     * @param id    the identifier
     */
    public EntityAlreadyExistsException(
            final Class<?> type, final Serializable id)
    {
        this(type.getTypeName(), id);
    }

    /**
     * Construct a {@code EntityAlreadyExistsException} with the specified
     * domain type, identifier and root cause.
     *
     * @param type   the domain type
     * @param id     the identifier
     * @param cause  the cause. It can be {@code null}
     */
    public EntityAlreadyExistsException(
            final Class<?> type, final Serializable id, final @Nullable Throwable cause)
    {
        this(type, id);
        initCause(cause);
    }

    /**
     * Lazy construct a {@code EntityAlreadyExistsException} with the specified
     * domain type and identifier.
     *
     * @param domain the domain type
     * @param id     the identifier
     */
    public static Supplier<? extends EntityAlreadyExistsException> creater( // NOSONAR
            final String domain, final Serializable id)
    {
        return () -> new EntityAlreadyExistsException(domain, id);
    }

    /**
     * Lazy construct a {@code EntityAlreadyExistsException} with the specified
     * domain type and identifier.
     *
     * @param type  the domain type
     * @param id    the identifier
     */
    public static Supplier<? extends EntityAlreadyExistsException> creater( // NOSONAR
            final Class<?> type, final Serializable id)
    {
        return () -> new EntityAlreadyExistsException(type, id);
    }

    /**
     * Lazy construct a {@code EntityAlreadyExistsException} with the specified
     * domain type, identifier and root cause.
     *
     * @param domain the domain type
     * @param id     the identifier
     * @param cause  the root cause. It can be {@code null}
     */
    public static Supplier<? extends EntityAlreadyExistsException> creater( // NOSONAR
            final String domain, final Serializable id, final @Nullable Throwable cause)
    {
        return () -> new EntityAlreadyExistsException(domain, id, cause);
    }

    /**
     * Lazy construct a {@code EntityAlreadyExistsException} with the specified
     * domain type, identifier and root cause.
     *
     * @param type   the domain type
     * @param id     the identifier
     * @param cause  the root cause. It can be {@code null}
     */
    public static Supplier<? extends EntityAlreadyExistsException> creater( // NOSONAR
            final Class<?> type, final Serializable id, final @Nullable Throwable cause)
    {
        return () -> new EntityAlreadyExistsException(type, id, cause);
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
