package io.davorpatech.apps.musicalsurveyor.services.colors;

import io.davorpatech.apps.musicalsurveyor.domain.RaffleTicketConstants;
import io.davorpatech.apps.musicalsurveyor.domain.colors.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.ColorRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.RaffleTicketRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Color;
import io.davorpatech.fwk.exception.EntityUsedByForeignsException;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Implementation of {@link ColorService}.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage {@link Color} data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class ColorServiceImpl extends JpaBasedDataService<
        ColorRepository,
        Long, Color, ColorDTO,
        FindColorsInput, CreateColorInput, UpdateColorInput>
    implements ColorService // NOSONAR
{

    private final RaffleTicketRepository raffleTicketRepository;

    /**
     * Constructs a new {@link ColorServiceImpl} with the given arguments.
     *
     * @param colorRepository the color repository, never {@code null}
     */
    ColorServiceImpl(ColorRepository colorRepository,
                     RaffleTicketRepository raffleTicketRepository)
    {
        super(colorRepository, ColorConstants.DOMAIN_NAME);
        Assert.notNull(raffleTicketRepository, "RaffleTicketRepository must not be null!");
        this.raffleTicketRepository = raffleTicketRepository;
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(Sort.Order.asc("code"));
    }

    @Override
    protected @NonNull ColorDTO convertEntityToDto(@NonNull Color entity) {
        return new ColorDTO(
                entity.getId(),
                entity.getCode());
    }

    @Transactional
    @Override
    public @NonNull ColorDTO create(@NonNull CreateColorInput input) {
        try {
            return super.create(input);
        } catch (DataIntegrityViolationException ex) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "UK_COLOR_CODE")) {
                throw new ColorCodeAlreadyExistsException(input.getCode(), ex);
            }
            throw ex;
        }
    }

    @Override
    protected @NonNull Color convertCreateToEntity(@NonNull CreateColorInput input) {
        Color entity = new Color();
        entity.setCode(input.getCode());
        return entity;
    }

    @Transactional
    @Override
    public @NonNull ColorDTO update(@NonNull UpdateColorInput input) {
        try {
            ColorDTO dto = super.update(input);
            // To be able to capture exceptions like DataIntegrityViolationException
            repository.flush();
            return dto;
        } catch (DataIntegrityViolationException ex) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "UK_COLOR_CODE")) {
                throw new ColorCodeAlreadyExistsException(input.getCode(), ex);
            }
            throw ex;
        }
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Color entity, @NonNull UpdateColorInput input) {
        String currentCode = entity.getCode();
        String updatedCode = input.getCode();
        if (!StringUtils.equalsIgnoreCase(currentCode, updatedCode)) {
            // there are a business restriction that says... "color codes are immutable"
            throw new ImmutableColorCodeException(entity.getId(), currentCode, updatedCode);
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
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "FK_RAFFLE_TICKET_COLOR_ID")
                    && (count = raffleTicketRepository.countByColor(id)) > 0) {
                throw new EntityUsedByForeignsException(domainName, id,
                    RaffleTicketConstants.DOMAIN_NAME, count, ex);
            }
            throw ex;
        }
    }
}
