package io.davorpatech.apps.musicalsurveyor.domain.prizes;

import io.davorpatech.fwk.model.BaseValueObject;
import io.davorpatech.fwk.model.commands.BaseSortableFindInputCmd;
import jakarta.persistence.Id;

import java.io.Serial;

/**
 * Input object for finding {@code Prize} instances.
 *
 * <p>As a domain DTO, it follows the {@link BaseValueObject} contract,
 * which means that it identifiable field is fuzzy, and it can be compared
 * for equality to other domain DTOs using all of its fields.
 */
public class FindPrizesInput extends BaseSortableFindInputCmd // NOSONAR
{ 
  private Long id;

  public FindPrizesInput() {
      super();
  }

  public FindPrizesInput(Long id) {
      super();
      this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id; 
  }

}
    @Serial
    private static final long serialVersionUID = -1005702181697836040L;

   
    public FindPrizesInput(int Id, Id id) {
        super(id);
    }
}
