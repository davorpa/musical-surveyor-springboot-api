package io.davorpatech.apps.musicalsurveyor.services.colors;

import io.davorpatech.apps.musicalsurveyor.domain.colors.ColorDTO;
import io.davorpatech.apps.musicalsurveyor.domain.colors.CreateColorInput;
import io.davorpatech.apps.musicalsurveyor.domain.colors.FindColorsInput;
import io.davorpatech.apps.musicalsurveyor.domain.colors.UpdateColorInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Color;
import io.davorpatech.fwk.service.data.DataService;

/**
 * Service for managing {@link Color} data domain.
 */
public interface ColorService extends DataService<
        Long, Color, ColorDTO,
        FindColorsInput, CreateColorInput, UpdateColorInput> // NOSONAR
{

}
