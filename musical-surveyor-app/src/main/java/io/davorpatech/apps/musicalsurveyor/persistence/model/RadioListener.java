package io.davorpatech.apps.musicalsurveyor.persistence.model;

import io.davorpatech.apps.musicalsurveyor.domain.listeners.RadioListenerConstants;
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

/**
 * The RadioListener entity class.
 *
 * <p>In a context of a radio station, a radio listener is a person who
 * listens the music that the radio station broadcasts.
 *
 * <p>A radio listener can participate in many surveys sending it favorite songs.
 * By sending that survey responses, participants gets a raffle ticket with can be
 * used to win a prize after a draw.
 *
 * <p>As an entity, follows the {@link BaseEntity} contract, which means
 * that it has an ID, and it can be compared for equality to other entities
 * using that identifiable field.
 */
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
     * Gets the full name of the radio listener.
     *
     * @return the full name of the radio listener
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the radio listener.
     *
     * @param name the full name of the radio listener to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the radio listener.
     *
     * @return the phone number of the radio listener
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the radio listener.
     *
     * <p>It must be a valid phone number.
     *
     * @param phone the phone number of the radio listener to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email address of the radio listener.
     *
     * @return the email address of the radio listener
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the radio listener.
     *
     * <p>It must be a valid email address and unique.
     *
     * @param email the email address of the radio listener to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the optional address of the radio listener.
     *
     * @return the address of the radio listener
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the optional address of the radio listener.
     *
     * @param address the address of the radio listener to set, it can be {@code null}
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the survey participations of the radio listener.
     *
     * <p>The returned collection is unmodifiable copy of the original.
     *
     * @return the survey participations of the radio listener
     */
    public Set<SurveyParticipation> getParticipations() {
        return Set.copyOf(participations);
    }

    /**
     * Sets the survey participations of the radio listener.
     *
     * @param participations the survey participations of the radio listener to set,
     *                       must not be {@code null}
     */
    public void setParticipations(Set<SurveyParticipation> participations) {
        this.participations = Objects.requireNonNull(
            participations, "participations must not be null!");
    }

    /**
     * Adds a survey participation to the radio listener.
     *
     * <p>As a side effect, it also sets the radio listener as the participant.
     *
     * @param participation the survey participation to add, must not be {@code null}
     */
    public void addParticipation(SurveyParticipation participation) {
        Objects.requireNonNull(participation, "participation to add must not be null!");
        participations.add(participation); // register
        participation.setParticipant(this); // link to this reference
    }

    /**
     * Removes a survey participation from the radio listener.
     *
     * <p>As a side effect, it also unsets the radio listener as the participant.
     *
     * @param participation the survey participation to remove, must not be {@code null}
     */
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
