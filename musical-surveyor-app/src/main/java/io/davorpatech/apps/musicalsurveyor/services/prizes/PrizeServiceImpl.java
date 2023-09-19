package io.davorpatech.apps.musicalsurveyor.services.prizes;


import io.davorpatech.apps.musicalsurveyor.domain.prizes.CreatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.FindPrizesInput;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.PrizeDTO;
import io.davorpatech.apps.musicalsurveyor.domain.prizes.UpdatePrizeInput;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.PrizeRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.RafflePrizeRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.RaffleTicketRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Prize;
import io.davorpatech.fwk.exception.EntityUsedByForeignsException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import io.micrometer.common.lang.NonNull;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class PrizeServiceImpl extends JpaBasedDataService<
    PrizeRepository,
        Long, Prize, PrizeDTO,
    FindPrizesInput, CreatePrizeInput, UpdatePrizeInput>
    implements PrizeService // NOSONAR
{

    private final RaffleTicketRepository raffleTicketRepository;

    /**
     * Constructs a new {@link PrizeServiceImpl} with the given arguments.
     *
     * @param prizeRepository the prize repository, never {@code null}
     */
    PrizeServiceImpl(PrizeRepository prizeRepository,
                     RafflePrizeRepository rafflePrizeRepository)
    {
        super(prizeRepository, PrizeConstants.DOMAIN_NAME);
        Assert.notNull(rafflePrizeRepository, "RafflePrizeRepository must not be null!");
        this.rafflePrizeRepository = rafflePrizeRepository;
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(Sort.Order.asc("code"));
    }

    @Override
    protected @NonNull PrizeDTO convertEntityToDto(@NonNull Prize entity) {
        return new PrizeDTO(
                entity.getId(),
                entity.getCode());
    }

    @Transactional
    @Override
    public @NonNull PrizeDTO create(@NonNull CreatePrizeInput input) {
        try {
            return super.create(input);
        } catch (DataIntegrityViolationException ex) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "UK_PRIZE_CODE")) {
                throw new PrizeCodeAlreadyExistsException(input.getCode(), ex);
            }
            throw ex;
        }
    }

    @Override
    protected @NonNull Prize convertCreateToEntity(@NonNull CreatePrizeInput input) {
        Prize entity = new Prize();
        entity.setCode(input.getCode());
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
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "UK_PRIZE_CODE")) {
                throw new PrizeCodeAlreadyExistsException(input.getCode(), ex);
            }
            throw ex;
        }
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Prize entity, @NonNull UpdatePrizeInput input) {
        String currentCode = entity.getCode();
        String updatedCode = input.getCode();
        if (!StringUtils.equalsIgnoreCase(currentCode, updatedCode)) {
            // there are a business restriction that says... "prize codes are immutable"
            throw new ImmutablePrizeCodeException(entity.getId(), currentCode, updatedCode);
        }
        entity.setCode(updatedCode);
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
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "FK_RAFFLE_TICKET_PRIZE_ID")
                    && (count = raffleTicketRepository.countByPrize(id)) > 0) {
                throw new EntityUsedByForeignsException(domainName, id,
                    RaffleTicketConstants.DOMAIN_NAME, count, ex);
            }
            throw ex;
        }
    }
}
