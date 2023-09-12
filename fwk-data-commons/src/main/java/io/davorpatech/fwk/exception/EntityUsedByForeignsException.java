package io.davorpatech.fwk.exception;

import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import io.davorpatech.fwk.model.ErrorDomain;
import io.davorpatech.fwk.model.Identifiable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.String.format;

/**
 * Exception that is thrown when the detail of a specific model object
 * (entity, POJO, DTO...) is already linked to other resource.
 *
 * <p>This will normally be used to indicate that a database Foreign Key
 * has been violated.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityUsedByForeignsException // NOSONAR
        extends PreconditionalException // NOSONAR
        implements Identifiable<Serializable>, ErrorDomain, AdditionalArgumentsPopulator // NOSONAR
{
    private static final long serialVersionUID = 1034016144102997744L;

    private final String domain;

    private final Serializable id;

    private final String foreignType;

    private final Long foreignCount;

    /**
     * Construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier and the foreignal data.
     *
     * @param domain       the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     */
    public EntityUsedByForeignsException(
            final String domain, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount)
    {
        super(format("The `%s` identified by `%s` is in use by %s.",
                domain, id, formatForeigns(foreignType, foreignCount)));
        this.domain = domain;
        this.id = id;
        this.foreignType = foreignType;
        this.foreignCount = foreignCount;
    }

    /**
     * Construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier, the foreignal data and root cause.
     *
     * @param domain       the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     * @param cause        the cause. It can be {@code null}
     */
    public EntityUsedByForeignsException(
            final String domain, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount,
            final @Nullable Throwable cause)
    {
        this(domain, id, foreignType, foreignCount);
        initCause(cause);
    }

    /**
     * Construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier, the foreignal data and identifier.
     *
     * @param type         the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     */
    public EntityUsedByForeignsException(
            final Class<?> type, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount)
    {
        this(type.getTypeName(), id, foreignType, foreignCount);
    }

    /**
     * Construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier, the foreignal data and root cause.
     *
     * @param type         the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     * @param cause        the cause. It can be {@code null}
     */
    public EntityUsedByForeignsException(
            final Class<?> type, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount,
            final @Nullable Throwable cause)
    {
        this(type, id, foreignType, foreignCount);
        initCause(cause);
    }

    protected static String formatForeigns(
            final String foreignType,
            final @Nullable Long foreignCount)
    {
        if (foreignCount == null) {
            return format("`%s`", foreignType);
        }
        // pluralize
        if (foreignCount == 1L) {
            return format("one `%s` item", foreignType);
        }
        return format("%s `%s` items", foreignCount, foreignType);
    }

    /**
     * Lazy construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier and the foreignal data.
     *
     * @param domain       the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     */
    public static Supplier<? extends EntityUsedByForeignsException> creater( // NOSONAR
            final String domain, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount)
    {
        return () -> new EntityUsedByForeignsException(
                domain, id, foreignType, foreignCount);
    }

    /**
     * Lazy construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier and the foreignal data.
     *
     * @param type         the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     */
    public static Supplier<? extends EntityUsedByForeignsException> creater( // NOSONAR
            final Class<?> type, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount)
    {
        return () -> new EntityUsedByForeignsException(
                type, id, foreignType, foreignCount);
    }

    /**
     * Lazy construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier, the foreignal data and root cause.
     *
     * @param domain       the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     * @param cause        the root cause. It can be {@code null}
     */
    public static Supplier<? extends EntityUsedByForeignsException> creater( // NOSONAR
            final String domain, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount,
            final @Nullable Throwable cause)
    {
        return () -> new EntityUsedByForeignsException(
                domain, id, foreignType, foreignCount, cause);
    }

    /**
     * Lazy construct a {@code EntityUsedByForeignsException} with the specified
     * domain type, its identifier, the foreignal data and root cause.
     *
     * @param type         the domain type
     * @param id           the identifier
     * @param foreignType  the domain type of foreignal data
     * @param foreignCount how many foreign entities are linked with given
     *                     {@code domain}. A positive number or {@code null}
     * @param cause        the root cause. It can be {@code null}
     */
    public static Supplier<? extends EntityUsedByForeignsException> creater( // NOSONAR
            final Class<?> type, final Serializable id,
            final String foreignType, final @Nullable Long foreignCount,
            final @Nullable Throwable cause)
    {
        return () -> new EntityUsedByForeignsException(
                type, id, foreignType, foreignCount, cause);
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

    public String getForeignType()
    {
        return foreignType;
    }

    public Long getForeignCount()
    {
        return foreignCount;
    }

    @Override
    public void populate(
            final Environment environment,
            final Map<String, Object> attributes)
    {
        attributes.put("foreignType", foreignType);
        if (foreignCount != null) {
            attributes.put("foreignCount", foreignCount);
        }
    }
}
