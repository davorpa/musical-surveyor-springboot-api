package io.davorpatech.apps.musicalsurveyor.services.listeners;

import io.davorpatech.apps.musicalsurveyor.domain.listeners.CreateRadioListenerInput;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.FindRadioListenerInput;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.RadioListenerDTO;
import io.davorpatech.apps.musicalsurveyor.domain.listeners.UpdateRadioListenerInput;
import io.davorpatech.apps.musicalsurveyor.persistence.model.RadioListener;
import io.davorpatech.fwk.service.data.DataService;

/**
 * Service for managing {@link RadioListener} data domain.
 */
public interface RadioListenerService extends DataService<
        Long, RadioListener, RadioListenerDTO,
        FindRadioListenerInput, CreateRadioListenerInput,UpdateRadioListenerInput>  // NOSONAR
{
}
