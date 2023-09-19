package io.davorpatech.apps.musicalsurveyor.domain.listeners;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.CreateInputCmd;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.Objects;

/**
 * Input objet for creating a new {@code RadioListener}
 * <p>Domain DTOS are immutable objects. As a DTO, it is a simple
 *  * POJO that holds data and has no behavior. It is used to transfer
 *  * data between the presentation layer and the services layer. It is
 *  * also used to validate the data sent to the services layer. It is a
 *  * good practice to use different DTOs for each one of service operations.
 *  * This way, the DTOs can be extended in the future without breaking the
 *  * service contract.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 *  * which means that it identifiable field is fuzzy, and it can be compared
 *  *
 *  * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 *  * which means that it identifiable field is fuzzy, and it can be compared
 *  * for equality to other domain DTOs using all of its fields.
 */
public class CreateRadioListenerInput extends BaseValueObject implements CreateInputCmd // NOSONAR
 {

     @Serial
     private static final long serialVersionUID = 9071775290398728529L;

     @NotBlank
     @Size(max = RadioListenerConstants.NAME_MAXLEN)
     private  final String name;

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

     @Override
     protected String defineObjAttrs() {
         return null;
     }

     /**
      * Constructs a new {@link CreateRadioListenerInput} with the giver arguments.
      * @param name the radio Listener name
      * @param phone the radio Listener phone
      * @param address the radio Listener address
      * @param email the radio listener email
      */

     public CreateRadioListenerInput(String name, String phone, String address, String email){
         super();
         this.name = name;
         this.phone = phone;
         this.address = address;
         this.email = email;
     }

     @Override
     public  boolean equals(Object o){
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         CreateRadioListenerInput other = (CreateRadioListenerInput) o;
         return Objects.equals(name, other.name) &&
             Objects.equals(phone, other.phone) &&
             Objects.equals(address, other.address)&&
             Objects.equals(email, other.email);
     }
     @Override
     public int hashCode(){
         return Objects.hash(name, phone, address, email);
     }

     /**
      * Returns the radio listener name.
      * @return the radio listener name
      */

        public String getName() {
            return name;
        }
        /**
         * Returns the radio listener phone.
         * @return the radio listener phone
         */
        public String getPhone() {
            return phone;
        }
            /**
             * Returns the radio listener address.
             * @return the radio listener address
             */
        public String getAddress() {
                return address;
            }

            /**
             * Returns the radio listener email.
             * @return the radio listener email
             */

        public String getEmail() {
                return email;
            }
 }


