package io.davorpatech.fwk.service.data;

import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.model.Entitier;
import io.davorpatech.fwk.model.PagedResult;
import io.davorpatech.fwk.model.ValueObject;
import io.davorpatech.fwk.model.commands.CreateInputCmd;
import io.davorpatech.fwk.model.commands.FindInputCmd;
import io.davorpatech.fwk.model.commands.UpdateInputCmd;
import io.davorpatech.fwk.service.Service;
import io.davorpatech.fwk.validation.ValidatedGroups;
import io.davorpatech.fwk.validation.groups.OnCreate;
import io.davorpatech.fwk.validation.groups.OnDelete;
import io.davorpatech.fwk.validation.groups.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * Contract for those {@literal Service}s that work on a concrete
 * business domain model.
 *
 * @param <ID> component type of the field that uniquely identifies said entity
 * @param <T> component type representing the domain entity
 * @param <DTO> component type representing the domain data transfer object
 * @param <FIND_CMD> type of the find input command
 * @param <CREATE_CMD> type of the create input command
 * @param <UPDATE_CMD> type of the update input command
 */
@Validated({ Default.class })
public interface DataService< // NOSONAR
                ID  extends Serializable, // NOSONAR
                T   extends Entitier<ID>, // NOSONAR
                DTO extends ValueObject, // NOSONAR
                FIND_CMD   extends FindInputCmd, // NOSONAR
                CREATE_CMD extends CreateInputCmd, // NOSONAR
                UPDATE_CMD extends UpdateInputCmd<ID> // NOSONAR
            > // NOSONAR
        extends Service // NOSONAR
{
    /**
     * Gets all records representing this domain data that matches query parameters.
     *
     * @param query the find query command used to sort, filtering and paging data
     * @return a paged result with the elements, never {@code null}.
     */
    @NonNull
    PagedResult<DTO> findAll(
            final @NonNull @Valid FIND_CMD query);

    /**
     * Finds a record by its identifier.
     *
     * @param id the identifier of record to retrieve, never {@code null}
     * @throws NoSuchEntityException if the record identified by the given
     *         {@literal id} is not found
     */
    @NonNull
    DTO findById(
            final @NonNull @Valid ID id);

    /**
     * Creates a new record.
     *
     * @param input the data object to persist, never {@code null}
     * @return the persisted data object, never {@code null}
     */
    @ValidatedGroups({ OnCreate.class })
    @NonNull
    DTO create(
            final @NonNull @Valid CREATE_CMD input);

    /**
     * Updates an existent record.
     *
     * @param input the data object to merge, never {@code null}
     * @return the merged data object, never {@code null}
     * @throws NoSuchEntityException if the record identified by the given
     *         {@code input.id} is not found
     */
    @ValidatedGroups({ OnUpdate.class })
    @NonNull
    DTO update(
            final @NonNull @Valid UPDATE_CMD input);

    /**
     * Removes an existent record given it identifier.
     *
     * @param id the identifier of record to remove, never {@code null}
     * @throws NoSuchEntityException if the record identified by the given
     *         {@literal id} is not found
     */
    @ValidatedGroups({ OnDelete.class })
    void deleteById(
            final @NonNull @Valid ID id);
}
