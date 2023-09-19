package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Objects;

public class RadioListenerDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = 7717262101391074640L;

    @Schema(

        description = "The listener ID",
        example = "1")
    private final Long id;

    @Schema(
        description = "The Listener name",
        example = "Beatle Greetings")
    private final String name;

    @Schema(
        description = "The Listener phone",
        example = "123456789")
    private final String phone;

    @Schema(
        description = "The Listener address",
        example = "1234 Abbey Road")
    private final String adress;

    @Schema(
        description = "The Listener email",
        example = "beatle@gmail.com")
    private final String email;

    /**
     * Constructs a new {@link RadioListenerDTO} with the given arguments.
     * @param id the Listener ID
     * @param name the Listener name
     * @param phone the Listener phone
     * @param adress the Listener address
     * @param email the Lsteners email
     */
    public RadioListenerDTO(Long id, String name, String phone, String adress, String email) {
       super();
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RadioListenerDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(adress, that.adress) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, phone, adress, email);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, name=%s, phone=%s, adress=%s, email=%s",
            id, name, phone, adress, email);
    }

    @Override
    public Long getId() {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

    public String getEmail() {
        return email;
    }
}

