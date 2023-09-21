package io.davorpatech.apps.musicalsurveyor.services.prizes;

import io.davorpatech.apps.musicalsurveyor.domain.prizes.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.PrizeRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Prize;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link PrizeService}.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage {@link Prize} data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class PrizeServiceImpl extends JpaBasedDataService<
        PrizeRepository,
        Long, Prize, PrizeDTO,
        FindPrizesInput, CreatePrizeInput, UpdatePrizeInput> // NOSONAR
    implements PrizeService
{
    /**
     * Constructs a new {@link PrizeServiceImpl} with the given arguments.
     *
     * @param prizeRepository the prize repository, never {@code null}
     */
    PrizeServiceImpl(PrizeRepository prizeRepository) {
        super(prizeRepository, PrizeConstants.DOMAIN_NAME);
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(
            Sort.Order.desc("monetaryValue"),
            Sort.Order.asc("title"));
    }


    @Override
    protected @NonNull PrizeDTO convertEntityToDto(@NonNull Prize prize) {
        return new PrizeDTO(
            prize.getId(),
            prize.getTitle(),
            prize.getDescription(),
            prize.getMonetaryValue());
    }

    @Override
    protected @NonNull Prize convertCreateToEntity(@NonNull CreatePrizeInput input) {
        Prize entity = new Prize();
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());
        entity.setMonetaryValue(input.getMonetaryValue());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Prize entity, @NonNull UpdatePrizeInput input) {
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());
        entity.setMonetaryValue(input.getMonetaryValue());
    }
}
