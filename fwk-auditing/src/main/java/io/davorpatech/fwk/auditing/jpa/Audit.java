package io.davorpatech.fwk.auditing.jpa;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

/**
 * Audit aggregated info processed by the
 * {@link org.springframework.data.jpa.domain.support.AuditingEntityListener
 * AuditingEntityListener}.
 */
@AccessType(AccessType.Type.FIELD)
@Embeddable
public class Audit extends BaseValueObject // NOSONAR
{
    private static final long serialVersionUID = -1893487022570484750L;

    @CreatedDate
    @Column(name = "created_on", nullable = false, insertable = true, updatable = false)
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(name = "created_by", nullable = false, insertable = true, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_on", nullable = true, insertable = true, updatable = true)
    private LocalDateTime lastModifiedOn;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = true, insertable = true, updatable = true)
    private String lastModifiedBy;

    @Override
    protected String defineObjAttrs() {
        return String.format("createdOn='%s', createdBy='%s', lastModifiedOn='%s', lastModifiedBy='%s'",
                createdOn, createdBy, lastModifiedOn, lastModifiedBy);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }
}
