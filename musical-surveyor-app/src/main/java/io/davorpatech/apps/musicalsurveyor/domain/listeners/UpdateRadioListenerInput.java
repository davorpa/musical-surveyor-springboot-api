package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;
/**
 * Input object for updating {@code RadioListener} instances.
 *
 * <p>Domain DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the presentation layer and the services layer. It is
 * also used to validate the data sent to the services layer. It is a
 * good practice to use different DTOs for each one of service operations.
 * This way, the DTOs can be extended in the future without breaking the
 * service contract.
 *
 * <p>This is why the {@link CreateRadioListenerInput} and the {@link UpdateRadioListenerInput}
 * are different classes. They both represent the same data, but the
 * {@link UpdateRadioListenerInput} has an additional {@code id} field. This is
 * because the {@code id} is required to update an existing {@code RadioListener}.
 * The {@code id} is not required to create a new {@code RadioListener} because
 * the server will generate a new {@code id} for the new {@code RadioListener}.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class UpdateRadioListenerInput extends BaseUpdateInputCmd<Long> {
    @Serial
    private static final long serialVersionUID = 7937565639101713456L;

    @NotBlank
    @Size(max = RadioListenerConstants.NAME_MAXLEN)
    private final String name;

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
     * Constructs a new {@link UpdateRadioListenerInput} with the given arguments.
     * @param id
     * @param name
     * @param phone
     * @param address
     * @param email
     */
    public UpdateRadioListenerInput(Long id, String name, String phone, String address, String email) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateRadioListenerInput)) return false;
        if (!super.equals(o)) return false;
        UpdateRadioListenerInput that = (UpdateRadioListenerInput) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, phone, address, email);
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
