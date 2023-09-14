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
    name = "RADIO_LISTENER",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_radio_listener_email", columnNames = {"email"})
    }
)
public class RadioListener extends BaseEntity<Long> implements AuditAccessor // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -3973268284635855107L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(name = "phone", length = 15, nullable = false)
    @NotBlank
    @Size(max = 15)
    @Pattern(regexp = "^\\+[0-9]{9,}$")
    private String phone;

    @Column(name = "email", length = 255, nullable = false)
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @Column(name = "address", length = 500, nullable = true)
    @Size(max = 500)
    private String address;

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, name='%s', phone='%s', email='%s'",
            super.defineObjAttrs(), name, phone, email);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
