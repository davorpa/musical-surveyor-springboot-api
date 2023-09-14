package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.RadioListenerConstants;
import io.davorpatech.fwk.auditing.jpa.Audit;
import io.davorpatech.fwk.auditing.jpa.AuditAccessor;
import io.davorpatech.fwk.model.BaseEntity;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    @OrderBy("participatedAt ASC, id.surveyId ASC")
    private Set<@Valid SurveyParticipation> participations = new LinkedHashSet<>();

    @Embedded
    private final Audit audit = new Audit();

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "%s, name='%s', phone='%s', email='%s', participations=%s",
            super.defineObjAttrs(), name, phone, email, participations.size());
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

    public Set<SurveyParticipation> getParticipations() {
        return Set.copyOf(participations);
    }

    public void setParticipations(Set<SurveyParticipation> participations) {
        this.participations = Objects.requireNonNull(
            participations, "participations must not be null!");
    }

    public void addParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to add must not be null!");
        participations.add(participation); // register
        participation.setParticipant(this); // link to this reference
    }

    public void removeParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to remove must not be null!");
        participations.remove(participation); // unregister
        participation.unsetParticipant(); // unlink this reference
    }

    @Override
    public Audit getAudit() {
        return audit;
    }
}
