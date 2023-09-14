package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

@EntityListeners({
    AuditingEntityListener.class
})
@Entity
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

    @Column(name = "code", nullable = false, length = 25)
    @NotBlank
    @Size(max = 25)
    @Pattern(regexp = "^(\\#[0-9A-Z]{6})$|^([a-z]+)$")
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public Audit getAudit() {
        return audit;
    }
}
