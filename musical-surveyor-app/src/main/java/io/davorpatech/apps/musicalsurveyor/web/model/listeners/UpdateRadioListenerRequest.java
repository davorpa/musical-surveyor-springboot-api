package io.davorpatech.apps.musicalsurveyor.web.model.listeners;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.RadioListenerConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Represents the HTTP request body to update an existing {@code RadioListener}.
 *
 * <p>Request DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the client and the server. It is also used to validate
 * the data sent to the server. It is a good practice to use different
 * DTOs for the request and the response. This way, the response DTO
 * can be extended in the future without breaking the client. This
 * is not the case for the request DTO, which should be kept as simple
 * as possible.
 *
 * <p>This is why the {@link CreateRadioListenerRequest} and the
 * {@link UpdateRadioListenerRequest} are different classes. They both
 * represent the same data, but the {@link UpdateRadioListenerRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code RadioListener}. The {@code id} is
 * not required to create a new {@code RadioListener} because the server
 * will generate a new {@code id} for the new {@code RadioListener}.
 */
@Schema(
    name = "UpdateRadioListenerRequest",
    description = "Represents the HTTP request body to update an existing RadioListener."
)
public class UpdateRadioListenerRequest extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -7757856597047267379L;

    @Schema(
        description = "The radio listener ID",
        example = "1",
        hidden = true)
    private final Long id;

    @Schema(
        description = "The radio listener name",
        example = "Beatle Greetings")
    @NotBlank
    @Size(max = RadioListenerConstants.NAME_MAXLEN)
    private final String name;

    @Schema(
        description = "The radio listener phone",
        example = "+34123456789")
   @NotBlank
   @Size(max = RadioListenerConstants.PHONE_MAXLEN)
    @Pattern(regexp = RadioListenerConstants.PHONE_REGEX)
    private final String phone;

    @Schema(
        description = "The radio listener address",
        example = """
            "123 Main St.""")
    @Size(max = RadioListenerConstants.ADDRESS_MAXLEN)
    private final String address;

    @Schema(
        description = "The radio listener email",
        example = """
            "exaple@gmail.com.""")
    @Size(max = RadioListenerConstants.EMAIL_MAXLEN)
    @Email
    private final String email;

    /**
     * Creates a new {@link UpdateRadioListenerRequest} instance with the given arguments.
     *
     * @param id        the radio listener ID
     * @param name      the radio listener name
     * @param phone     the radio listener phone
     * @param address   the radio listener address
     * @param email     the radio listener email
     */

    @JsonCreator
    public UpdateRadioListenerRequest(Long id, String name, String phone, String address, String email) {
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
        if (!(o instanceof UpdateRadioListenerRequest)) return false;
        UpdateRadioListenerRequest other = (UpdateRadioListenerRequest) o;
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
        return String.format("id=%s, name='%s', phone='%s', address='%s', email='%s'", id, name, phone, address, email);
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
     * Returns the radio listener name.
     *
     * @return the radio listener name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the radio listener phone.
     *
     * @return the radio listener phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Returns the radio listener address.
     *
     * @return the radio listener address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Returns the radio listener email.
     *
     * @return the radio listener email
     */
    public String getEmail() {
        return email;
    }
}
