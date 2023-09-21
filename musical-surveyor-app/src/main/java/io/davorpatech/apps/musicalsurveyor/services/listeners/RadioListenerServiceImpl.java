package io.davorpatech.apps.musicalsurveyor.services.listeners;

import io.davorpatech.apps.musicalsurveyor.domain.listeners.*;
import io.davorpatech.apps.musicalsurveyor.persistence.dao.RadioListenerRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.RadioListener;
import io.davorpatech.fwk.service.data.jpa.JpaBasedDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link RadioListenerService}.
 *
 * <p>As a service, it is a stateless class that provides operations
 * to manage {@link RadioListener} data domain.
 *
 * <p>Services are the entry point to the business logic. They are
 * responsible for handling the data flow between the presentation
 * layer and the persistence layer, and vice versa.
 */
@Service
@Transactional(readOnly = true)
public class RadioListenerServiceImpl extends JpaBasedDataService<
        RadioListenerRepository,
        Long, RadioListener, RadioListenerDTO,
        FindRadioListenersInput, CreateRadioListenerInput, UpdateRadioListenerInput>
    implements RadioListenerService // NOSONAR
{
    /**
     * Constructs a new {@link JpaBasedDataService} with the given arguments.
     *
     * @param radioListenerRepository the repository of the business entity, never {@code null}
     */
    RadioListenerServiceImpl(RadioListenerRepository radioListenerRepository) {
        super(radioListenerRepository, RadioListenerConstants.DOMAIN_NAME);
    }

    @Override
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.by(
            Sort.Order.asc("name"),
            Sort.Order.asc("id"));
    }

    @Override
    protected @NonNull RadioListenerDTO convertEntityToDto(@NonNull RadioListener entity) {
        return new RadioListenerDTO(
            entity.getId(),
            entity.getName(),
            entity.getPhone(),
            entity.getAddress(),
            entity.getEmail());
    }

    @Transactional
    @Override
    public @NonNull RadioListenerDTO create(@NonNull CreateRadioListenerInput input) {
        try {
            return super.create(input);
        } catch (DataIntegrityViolationException e) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(e.getMessage(), "UK_radio_listener_email")) {
                throw new EmailAlreadyExistException(input.getEmail(), e);
            }
            throw e;
        }
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

    @Transactional
    @Override
    public @NonNull RadioListenerDTO update(@NonNull UpdateRadioListenerInput input) {
        try {
            RadioListenerDTO dto = super.update(input);
            // To be able to capture exceptions like DataIntegrityViolationException
            repository.flush();
            return dto;
        } catch (DataIntegrityViolationException e) {
            // translate the exception to a more meaningful one
            if (StringUtils.containsIgnoreCase(e.getMessage(), "UK_radio_listener_email")) {
                throw new EmailAlreadyExistException(input.getEmail(), e);
            }
            throw e;
        }
    }


    @Override
    protected void populateEntityToUpdate(
            @NonNull RadioListener entity, @NonNull UpdateRadioListenerInput input) {
        entity.setName(input.getName());
        entity.setPhone(input.getPhone());
        entity.setAddress(input.getAddress());
        entity.setEmail(input.getEmail());
    }
}
