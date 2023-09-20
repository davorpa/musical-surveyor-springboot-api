package io.davorpatech.apps.musicalsurveyor.services.prizes;

import io.davorpatech.apps.musicalsurveyor.domain.prizes.CreatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.FindPrizesInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.PrizeDTO;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.UpdatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Prize;
import io.davorpatech.fwk.service.data.DataService;

/**
 * Service for managing {@link Prize} data domain.
 *
 * <p>Service interfaces are used to define the business logic of the application.
 * They are used to define the operations that can be performed on the domain
 * entities and DTOs.
 *
 * <p>Service interfaces are implemented by service classes.
 */
public interface PrizeService extends DataService<
        Long, Prize, PrizeDTO,
        FindPrizesInput, CreatePrizeInput, UpdatePrizeInput> // NOSONAR
{

}
