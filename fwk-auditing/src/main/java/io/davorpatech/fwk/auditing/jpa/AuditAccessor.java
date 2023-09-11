package io.davorpatech.fwk.auditing.jpa;

/**
 * Interface that provides access to JPA audit info.
 *
 * <p>Implemented entities capable of accessing audit information shall define
 * the {@link org.springframework.data.jpa.domain.support.AuditingEntityListener
 * AuditingEntityListener} entity listener among the
 * {@link jakarta.persistence.EntityListeners @EntityListeners} of the form:
 *
 * <pre>{@code
 * @EntityListeners({AuditingEntityListener.class})
 * @Entity
 * ...
 * }</pre>
 *
 * <p>In turn, an entity attribute will be declared as follows:
 * <pre>{@code
 * @Embedded
 * private final Audit audit = new Audit();
 * ...
 * @Override
 * public Audit getAudit() { return audit; }
 * }</pre>
 */
public interface AuditAccessor // NOSONAR
{
    /**
     * Gets the JPA audit info.
     *
     * @return the audit info
     */
    Audit getAudit();
}
