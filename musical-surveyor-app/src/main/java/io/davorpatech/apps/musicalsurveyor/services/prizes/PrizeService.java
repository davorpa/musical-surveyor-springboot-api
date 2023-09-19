package io.davorpatech.apps.musicalsurveyor.services.prizes;

import io.davorpatech.apps.musicalsurveyor.domain.prizes.PrizeDTO;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.CreatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.FindPrizesInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.UpdatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Prize;
import io.davorpatech.fwk.service.data.DataService;

/**
 * Service for managing {@link Prize} data domain.
 */
public interface PrizeService extends DataService<
        Long, Prize, PrizeDTO,
        FindPrizesInput, CreatePrizesInput, UpdatePrizeInput> // NOSONAR
{
