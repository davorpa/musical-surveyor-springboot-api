package io.davorpatech.fwk.service.data.jpa;

import io.davorpatech.fwk.exception.NoSuchEntityException;
import io.davorpatech.fwk.model.Entitier;
import io.davorpatech.fwk.model.PagedResult;
import io.davorpatech.fwk.model.ValueObject;
import io.davorpatech.fwk.model.commands.CreateInputCmd;
import io.davorpatech.fwk.model.commands.FindInputCmd;
import io.davorpatech.fwk.model.commands.Sortable;
import io.davorpatech.fwk.model.commands.UpdateInputCmd;
import io.davorpatech.fwk.service.ServiceCommonSupport;
import io.davorpatech.fwk.service.data.DataService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * Implementations must delegate most of their operations to the JPA repository of duty.
 *
 * @param <R> component type of the data repository
 * @param <ID> component type of the field that uniquely identifies said entity
 * @param <T> component type representing the domain entity
 * @param <DTO> component type representing the domain data transfer object
 * @param <FIND_CMD> type of the find input command
 * @param <CREATE_CMD> type of the create input command
 * @param <UPDATE_CMD> type of the update input command
 */
@Transactional(readOnly = true)
public abstract class JpaBasedDataService< // NOSONAR
                R extends JpaRepository<T, ID>, // NOSONAR
                ID  extends Serializable, // NOSONAR
                T   extends Entitier<ID>, // NOSONAR
                DTO extends ValueObject, // NOSONAR
                FIND_CMD   extends FindInputCmd, // NOSONAR
                CREATE_CMD extends CreateInputCmd, // NOSONAR
                UPDATE_CMD extends UpdateInputCmd<ID> // NOSONAR
            > // NOSONAR
        extends ServiceCommonSupport // NOSONAR
        implements DataService<ID, T, DTO, FIND_CMD, CREATE_CMD, UPDATE_CMD> // NOSONAR
{
    /**
     * The domain name of any business entity is a constant value that uniquely
     * identifies it in the entire application.
     */
    protected final String domainName;

    /**
     * The repository handling the data of this domain entity service.
     */
    protected final R repository;

    /**
     * Constructs a new {@link JpaBasedDataService} with the given arguments.
     *
     * @param repository the repository of the business entity, never {@code null}
     * @param domainName the domain name that uniquely identifies the underlayed
     *                   business entity
     */
    protected JpaBasedDataService(
            final R repository, final String domainName)
    {
        super();
        Assert.hasText(domainName, "Domain Name must not be blank!");
        Assert.notNull(repository, domainName + " Repository must not be null!");
        this.domainName = domainName;
        this.repository = repository;
    }

    @Override
    public @NonNull PagedResult<DTO> findAll(
            final @NonNull @Valid FIND_CMD query)
    {
        int pageSize = query.getPageSize();
        int pageNumber = query.getPageNumber();
        // compute find all sort argument
        final Sort sort = determineFindSort(query);
        // compute find all query example
        final Example<T> example = determineFindFilters(query);
        // do search using resolved find all arguments
        final Page<DTO> page;
        if (pageSize > 0) { // paged search
            final Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            page = (example == null
                        ? repository.findAll(pageable)
                        : repository.findAll(example, pageable))
                    .map(this::convertEntityToDto);
        } else { // unpaged search
            List<T> content = example == null
                    ? repository.findAll(sort)
                    : repository.findAll(example, sort);
            page = new PageImpl<>(content)
                    .map(this::convertEntityToDto);
        }
        // map page to paged result
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    /**
     * Determines the filtering example used in any search query.
     *
     * <p>Implementations that need query filtering should override this method
     * providing an {@link Example} with a {@link T} entity probe.
     *
     * @param query the find query DTO used make any search
     * @return {@code null} if filtering is disabled (default behaviour)
     */
    protected @Nullable Example<T> determineFindFilters(
            final @NonNull FIND_CMD query)
    {
        return null;
    }

    /**
     * Determines the sort properties used to make any search query.
     *
     * @param query the find query DTO used make any search
     * @return the sort properties holder, never {@code null}
     *
     * @see #getDefaultFindSort()
     */
    protected @NonNull Sort determineFindSort(
            final @NonNull FIND_CMD query)
    {
        Sort sort = null;
        if (query instanceof Sortable) { // NOSONAR
            sort = ((Sortable) query).getSort();
        }
        if (sort == null || sort.isEmpty()) {
            sort = getDefaultFindSort();
        }
        return sort;
    }

    /**
     * Gets the default sort properties used to make any search query.
     *
     * @return the default sort properties holder. Must not be null, use
     *         {@link Sort#unsorted()} instead, which is the default return
     *         value implemented in this method.
     *
     * @see Sort#unsorted()
     */
    protected @NonNull Sort getDefaultFindSort() {
        return Sort.unsorted();
    }

    @Override
    public @NonNull DTO findById(
            final @NonNull @Valid ID id)
    {
        return repository.findById(id)
                .map(this::convertEntityToDto)
                .orElseThrow(NoSuchEntityException.creater(domainName, id));
    }

    /**
     * Converts the business JPA entity into their DTO representation.
     *
     * @param entity the entity to convert from
     * @return the {@link DTO} representation related with the {@link T} entity
     */
    protected abstract @NonNull DTO convertEntityToDto(final @NonNull T entity);

    @Override
    @Transactional
    public @NonNull DTO create(
            final @NonNull @Valid CREATE_CMD input)
    {
        // map create DTO to entity
        T entity = convertCreateToEntity(input);
        // save/persist
        entity = repository.save(entity);
        // map persisted entity to dto
        return convertEntityToDto(entity);
    }

    /**
     * Converts the creation DTO into their business JPA entity.
     *
     * @param input the creation DTO input to convert from
     * @return the {@link T} representation related with the {@link CREATE_CMD} dto
     */
    protected abstract T convertCreateToEntity(CREATE_CMD input);

    @Override
    @Transactional
    public @NonNull DTO update(
            final @Valid UPDATE_CMD input)
    {
        final ID id = input.getId();
        // find record which operate with
        T entity = repository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(domainName, id));
        // transfer each update DTO field to entity record
        populateEntityToUpdate(entity, input);
        // save/merge
        entity = repository.save(entity);
        // map merged entity to dto
        return convertEntityToDto(entity);
    }

    /**
     * Transfers the update DTO input command properties to their correspondent
     * at related business JPA entity.
     *
     * @param entity the business entity to populate to
     * @param input the update DTO input to populate from
     */
    protected abstract void populateEntityToUpdate(
            final @NonNull T entity, final @NonNull UPDATE_CMD input);

    @Override
    @Transactional
    public void deleteById(
            final @NonNull @Valid ID id)
    {
        final T entity = repository.findById(id)
                .orElseThrow(NoSuchEntityException.creater(domainName, id));
        checkEntityDeletion(entity);
        repository.delete(entity);
    }

    /**
     * Checks if the given entity can be deleted.
     *
     * <p>Implementations that need to check if the entity can be deleted
     * should override this method.
     *
     * @param entity the entity to check
     */
    protected void checkEntityDeletion(final @NonNull T entity) {
        // NOOP
    }
}
