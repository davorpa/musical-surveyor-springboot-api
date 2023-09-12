package io.davorpatech.fwk.model;

import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * Interface representing a populator that can provide additional argument values.
 */
public interface AdditionalArgumentsPopulator
{
    /**
     * Populates the given attributes with additional values.
     *
     * @param environment the environment in which the current application is running
     * @param attributes the Map used to provide additional attributes
     */
    void populate(
            final @NonNull Environment environment,
            final @NonNull Map<String, Object> attributes);
}
