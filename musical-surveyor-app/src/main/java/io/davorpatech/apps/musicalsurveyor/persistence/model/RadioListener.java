package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.RadioListenerConstants;
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
@Entity(name = RadioListenerConstants.DOMAIN_NAME)
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

    @Column(name = "name", length = RadioListenerConstants.NAME_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = RadioListenerConstants.NAME_MAXLEN)
    private String name;

    @Column(name = "phone", length = RadioListenerConstants.PHONE_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = RadioListenerConstants.PHONE_MAXLEN)
    @Pattern(regexp = RadioListenerConstants.PHONE_REGEX)
    private String phone;

    @Column(name = "email", length = RadioListenerConstants.EMAIL_MAXLEN, nullable = false)
    @NotBlank
    @Size(max = RadioListenerConstants.EMAIL_MAXLEN)
    @Email
    private String email;

    @Column(name = "address", length = RadioListenerConstants.ADDRESS_MAXLEN, nullable = true)
    @Size(max = RadioListenerConstants.ADDRESS_MAXLEN)
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
