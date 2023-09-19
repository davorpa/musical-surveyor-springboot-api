package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreatePrizeInput {

@NotBlank
private String title;

@NotBlank
private String description;

@NotNull
@Positive
private Double monetaryValue;

public CreatePrizeInput() {}

public CreatePrizeInput(String title, String description, Double monetaryValue) {
this.title = title;
this.description = description;
this.monetaryValue = monetaryValue;
}

public String getTitle() {
return title;
}

public String getDescription() {
return description;
}

public Double getMonetaryValue() {
return monetaryValue;
}

}