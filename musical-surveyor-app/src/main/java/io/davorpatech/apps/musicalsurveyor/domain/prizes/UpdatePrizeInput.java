package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import io.davorpatech.fwk.model.commands.BaseUpdateInputCmd;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;

public class UpdatePrizeInput extends BaseUpdateInputCmd<Long> // NOSONAR
{

    @Serial
    private static final long serialVersionUID = 7132259547305535349L;
    @NotBlank
  private String title;

  @NotBlank
  private String description;  

  private Double monetaryValue;

  public UpdatePrizeInput() {
      super();
  }

  public UpdatePrizeInput(String title, String description, Double monetaryValue) {
      super();
      this.title = title;
    this.description = description;
    this.monetaryValue = monetaryValue;
  }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
// getters and setters


  public String getDescription() {...}

  public void setDescription(String description) {...}

  public Double getMonetaryValue() {...}

  public void setMonetaryValue(Double monetaryValue) {...}

}
