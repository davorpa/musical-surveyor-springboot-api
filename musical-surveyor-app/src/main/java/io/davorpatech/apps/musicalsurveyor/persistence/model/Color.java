package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.ColorConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * The Color entity class.
 *
 * <p>A color is a visual mask that can be applied to a raffle ticket.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
@EntityListeners({
    AuditingEntityListener.class
})
@Entity(name = ColorConstants.DOMAIN_NAME)
@Table(
    name = "COLOR",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_color_code", columnNames = {"code"})
    }
)
public class Color extends BaseEntity<Long> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1045774763290967055L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "code", nullable = false, length = ColorConstants.CODE_MAXLEN)
    @NotBlank
    @Size(max = ColorConstants.CODE_MAXLEN)
    @Pattern(regexp = ColorConstants.CODE_REGEX)
    private String code;

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, code='%s'", super.defineObjAttrs(), code);
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the entity.
     *
     * <p>It is not recommended to use this method directly, as it is
     * intended to be used by the persistence layer.
     *
     * @param id the ID of the entity to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the color code.
     *
     * <p>It is formatted as a hexadecimal color code, such as {@code #FFFFFF}
     * or as a color name like {@code white}.
     *
     * @return the color code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the color code.
     *
     * <p>It must be formatted as a hexadecimal color code, such as {@code #FFFFFF}
     * or as a color name like {@code white}.
     *
     * @param code the color code to set
     */
    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public Audit getAudit() {
        return audit;
    }
}
