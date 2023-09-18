package io.davorpatech.apps.musicalsurveyor.services.colors;

import io.davorpatech.apps.musicalsurveyor.domain.colors.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.ColorRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Color;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /**
     * Constructs a new {@link ColorServiceImpl} with the given arguments.
     *
     * @param colorRepository the color repository, never {@code null}
     */
    ColorServiceImpl(ColorRepository colorRepository)
    {
        super(colorRepository, ColorConstants.DOMAIN_NAME);
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

    @Override
    protected @NonNull Color convertCreateToEntity(@NonNull CreateColorInput input) {
        Color entity = new Color();
        entity.setCode(input.getCode());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(
            @NonNull Color entity, @NonNull UpdateColorInput input) {
        entity.setCode(input.getCode());
    }
}
