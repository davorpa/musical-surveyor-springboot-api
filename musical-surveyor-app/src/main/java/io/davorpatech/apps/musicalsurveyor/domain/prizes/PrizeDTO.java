package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.Identifiable;

import java.io.Serial;
import java.util.Objects;

public class PrizeDTO extends BaseValueObject implements Identifiable<Long> // NOSONAR
 {

     @Serial
     private static final long serialVersionUID = -9065944401552254860L;

     private final Long id;

     private final String title;

     private final String description;

     private final Double monetaryValue;


     public PrizeDTO(Long id, String title, String description, Double monetaryValue) {
         this.id = id;
         this.title = title;
         this.description = description;
         this.monetaryValue = monetaryValue;
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         PrizeDTO prizeDTO = (PrizeDTO) o;
         return Objects.equals(id, prizeDTO.id) && Objects.equals(title, prizeDTO.title) && Objects.equals(description, prizeDTO.description) && Objects.equals(monetaryValue, prizeDTO.monetaryValue);
     }

     @Override
     public int hashCode() {
         return Objects.hash(id, title, description, monetaryValue);
     }

     @Override
     protected String defineObjAttrs() {
         return   String.format("id=%s, title='%s', description='%s', monetaryValue=%s",
             id, title, description, monetaryValue);
     }

     @Override
     public Long getId() {
         return id;
     }

     public String getTitle() {
         return title;
     }

     public String getDescription() {
         return description;
     }

     /**
      * @return
      */
     public Double getMonetaryValue() {
         return monetaryValue;
     }
 }
