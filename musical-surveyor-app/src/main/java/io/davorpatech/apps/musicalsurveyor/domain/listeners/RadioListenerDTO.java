package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Objects;

/**
 * The Listener DTO class.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior.
 *
 * <p>It is used to transfer projected data between the persistence layer
 * and the service layer. Also, it transfers this aggregated data from the
 * service layer to the presentation layer.
 */
@Schema(
    name = "RadioListener",
    description = """
        In a context of a radio station, a radio listener is a person who
        listens the music that the radio station broadcasts.
        
        A radio listener can participate in many surveys sending it favorite songs.
        By sending that survey responses, participants gets a raffle ticket with can be
        used to win a prize after a draw.
        """
)
public class RadioListenerDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 7717262101391074640L;

    @Schema(
        description = "The radio listener ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The radio listener full name",
        example = "Paul McCartney")
    private final String name;

    @Schema(
        description = "The radio listener phone number",
        example = "+420123456789")
    private final String phone;

    @Schema(
        description = "The radio listener postal address",
        example = "1234 Abbey Road, Liverpool, UK")
    private final String address;

    @Schema(
        description = "The radio listener email address",
        example = "beatles@example.com")
    private final String email;

    /**
     * Constructs a new {@link RadioListenerDTO} with the given arguments.
     *
     * @param id      the radio listener ID
     * @param name    the radio listener name
     * @param phone   the radio listener phone number
     * @param address the radio listener postal address
     * @param email   the radio listener email address
     */
    public RadioListenerDTO(Long id, String name, String phone, String address, String email) {
       super();
       this.id = id;
       this.name = name;
       this.phone = phone;
       this.address = address;
       this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadioListenerDTO other = (RadioListenerDTO) o;
        return Objects.equals(id, other.id) &&
            Objects.equals(name, other.name) &&
            Objects.equals(phone, other.phone) &&
            Objects.equals(address, other.address) &&
            Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, address, email);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format(
            "id=%s, name='%s', phone='%s', email='%s', address=%s",
            id, name, phone, email,
            address == null ? null : '\'' + address + '\'');
    }

    /**
     * Returns the radio listener ID.
     *
     * @return the radio listener ID
     */
    @Override
    public Long getId() {
        return null;
    }

    /**
     * Returns the radio listener full name.
     *
     * @return the radio listener full name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the radio listener phone number.
     *
     * @return the radio listener phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the radio listener postal address.
     *
     * @return the radio listener postal address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the radio listener email address.
     *
     * @return the radio listener email address
     */
    public String getEmail() {
        return email;
    }
}
