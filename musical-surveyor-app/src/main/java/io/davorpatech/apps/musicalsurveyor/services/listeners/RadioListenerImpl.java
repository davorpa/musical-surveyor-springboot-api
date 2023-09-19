package io.davorpatech.apps.musicalsurveyor.services.listeners;

import io.davorpatech.apps.musicalsurveyor.domain.listeners.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.RadioListenerRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.RadioListener;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;

public class RadioListenerImpl extends JpaBasedDataService<
    RadioListenerRepository, Long, RadioListener, RadioListenerDTO,
    FindRadioListenerInput, CreateRadioListenerInput,UpdateRadioListenerInput> // NOSONAR
{
    /**
     * Constructs a new {@link JpaBasedDataService} with the given arguments.
     *
     * @param radioListenerRepository the repository of the business entity, never {@code null}
     */
     RadioListenerImpl(RadioListenerRepository radioListenerRepository){
        super(radioListenerRepository, RadioListenerConstants.DOMAIN_NAME );
    }

    @Override
    protected RadioListenerDTO convertEntityToDto(RadioListener entity) {
        return new RadioListenerDTO(
                entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getEmail());
    }

    @Override
    protected RadioListener convertCreateToEntity(CreateRadioListenerInput input) {
        RadioListener entity = new RadioListener();
        entity.setName(input.getName());
        entity.setPhone(input.getPhone());
        entity.setAddress(input.getAddress());
        entity.setEmail(input.getEmail());
        return entity;
    }

    @Override
    protected void populateEntityToUpdate(RadioListener entity, UpdateRadioListenerInput input) {
        entity.setName(input.getName());
        entity.setPhone(input.getPhone());
        entity.setAddress(input.getAddress());
        entity.setEmail(input.getEmail());
    }
}
