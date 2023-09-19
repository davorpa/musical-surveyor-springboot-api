package io.davorpatech.fwk.model.commands;

import io.davorpatech.fwk.model.BaseValueObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Basic implementation of an {@link UpdateInputCmd}.
 *
 * @param <ID> component type for the business entity ID.
 *
 * @see UpdateInputCmd
 */
public class BaseUpdateInputCmd<ID extends Serializable> // NOSONAR
        extends BaseValueObject // NOSONAR
        implements UpdateInputCmd<ID> // NOSONAR
{
    private static final long serialVersionUID = 1182779272517072715L;

    @NotNull
    @Valid
    private final ID id;

    /**
     * Constructs a new {@link BaseUpdateInputCmd} with the given arguments.
     */
    public BaseUpdateInputCmd() {
        super();
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUpdateInputCmd other = (BaseUpdateInputCmd) o; // NOSONAR
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s", id);
    }

    @Override
    public ID getId() {
        return id;
    }
}
