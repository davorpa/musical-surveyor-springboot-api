package io.davorpatech.apps.musicalsurveyor.web.model.listeners;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.RadioListenerConstants;
import io.davorpatech.fwk.model.BaseValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Represents the HTTP request body to create a new {@code RadioListener}.
 *
 * <p>Request DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the client and the server. It is also used to validate
 * the data sent to the server. It is a good practice to use different
 * DTOs for the request and the response. This way, the response DTO
 * can be extended in the future without breaking the client. This
 * is not the case for the request DTO, which should be kept as simple
 *as possible.
 *
 * <p>This is why the {@link CreateRadioListenerRequest} and the
 * {@link U} are different classes. They both
 * represent the same data, but the {@link UpdateRadioListenerRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code RadioListener}. The {@code id} is
 * not required to create a new {@code RadioListener} because the server
 * will generate a new {@code id} for the new {@code RadioListener}.
 */
@Schema(
    name = "CreateRadioListenerRequest",
    description = "Represents the HTTP request body to create a new RadioListener."
)
public class CreateRadioListenerRequest extends BaseValueObject // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 2229201325724357216L;

    @Schema(
        description = "The radio listener name",
        example = "Beatle Greetings")
    @NotBlank
    @Size(max = RadioListenerConstants.NAME_MAXLEN)
    private final String name;

    @Schema(
        description = "The radio listener phone number",
        example = "+34123456789")
   @NotBlank
    @Size(max = RadioListenerConstants.PHONE_MAXLEN)
    @Pattern(regexp = RadioListenerConstants.PHONE_REGEX)
    private final String phone;

    @Size(max = RadioListenerConstants.ADDRESS_MAXLEN)
    private final String address;

    @NotBlank
    @Size(max = RadioListenerConstants.EMAIL_MAXLEN)
    @Email
    private final String email;

    /**
     * Creates a new {@link CreateRadioListenerRequest} instance with the given arguments.
     *
     * @param name      the radio listener name
     * @param phone the radio listener phone
     * @param address the radio listener address
     * @praam email the radio listener email
     */
    @JsonCreator
    public CreateRadioListenerRequest(String name, String phone, String address, String email) {
        super();
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateRadioListenerRequest)) return false;
        CreateRadioListenerRequest other = (CreateRadioListenerRequest) o;
        return Objects.equals(name, other.name) &&
            Objects.equals(phone, other.phone) &&
            Objects.equals(address, other.address) &&
            Objects.equals(email, other.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, phone, address, email);
    }
    @Override
    protected String defineObjAttrs() {
        return String.format("name='%s', phone='%s', address='%s', email='%s'", name, phone, address, email);
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
