package io.davorpatech.apps.musicalsurveyor.services.prizes;

import io.davorpatech.apps.musicalsurveyor.domain.RafflePrizeConstants;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.PrizeRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.RafflePrizeRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Prize;
import io.davorpatech.fwk.exception.EntityUsedByForeignsException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import io.micrometer.common.lang.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    private final RafflePrizeRepository rafflePrizeRepository;

    /**
     * Constructs a new {@link PrizeServiceImpl} with the given arguments.
     *
     * @param prizeRepository the prize repository, never {@code null}
     */
    PrizeServiceImpl(PrizeRepository prizeRepository,
                     RafflePrizeRepository rafflePrizeRepository) {
        super(prizeRepository, PrizeConstants.DOMAIN_NAME);
        Assert.notNull(rafflePrizeRepository, "RafflePrizeRepository must not be null!");
        this.rafflePrizeRepository = rafflePrizeRepository;
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

    @Transactional
    @Override
    public @NonNull PrizeDTO create(@NonNull CreatePrizeInput input) {
        try {
            return super.create(input);
        } catch (DataIntegrityViolationException ex) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "UK_prize_title")) {
                throw new PrizeTitleAlreadyExistsException(input.getTitle(), ex);
            }
            throw ex;
        }
    }

    @Override
    protected @NonNull Prize convertCreateToEntity(@NonNull CreatePrizeInput input) {
        Prize entity = new Prize();
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());
        entity.setMonetaryValue(input.getMonetaryValue());
        return entity;
    }

    @Transactional
    @Override
    public @NonNull PrizeDTO update(@NonNull UpdatePrizeInput input) {
        try {
            PrizeDTO dto = super.update(input);
            // To be able to capture exceptions like DataIntegrityViolationException
            repository.flush();
            return dto;
        } catch (DataIntegrityViolationException ex) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "UK_prize_title")) {
                throw new PrizeTitleAlreadyExistsException(input.getTitle(), ex);
            }
            throw ex;
        }
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Prize entity, @NonNull UpdatePrizeInput input) {
        Long prizeId = entity.getId();
        if (rafflePrizeRepository.existsAwardedTicketsByPrize(prizeId)) {
            // business rule: a prize that has been awarded to some winner cannot be edited
            throw new UnableToEditLockedPrizeDetailException(prizeId,
                "Already awarded to some winner");
        }
        // populate the entity with the input data
        entity.setTitle(input.getTitle());
        entity.setDescription(input.getDescription());
        entity.setMonetaryValue(input.getMonetaryValue());
    }

    @Transactional
    @Override
    public void deleteById(@NonNull Long id) {
        try {
            super.deleteById(id);
            // To be able to capture exceptions like DataIntegrityViolationException
            repository.flush();
        } catch (DataIntegrityViolationException ex) {
            // translate the exception to a more meaningful one
            long count = -1;
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "FK_raffle_prize_prize_id")
                && (count = rafflePrizeRepository.countByPrize(id)) > 0) {
                throw new EntityUsedByForeignsException(domainName, id,
                    RafflePrizeConstants.DOMAIN_NAME, count, ex);
            }
            throw ex;
        }
    }
}
