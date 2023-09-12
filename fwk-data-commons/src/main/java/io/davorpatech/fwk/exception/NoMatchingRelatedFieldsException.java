package io.davorpatech.fwk.exception;

import io.davorpatech.fwk.model.AdditionalArgumentsPopulator;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Map;

import static java.lang.String.format;

/**
 * Exception raised when the two related fields don't match.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoMatchingRelatedFieldsException // NOSONAR
        extends PreconditionalException // NOSONAR
        implements AdditionalArgumentsPopulator // NOSONAR
{
    private static final long serialVersionUID = 6546909235713790938L;

    private final String expectedFieldName;

    private final Serializable expectedFieldValue;

    private final String actualFieldName;

    private final Serializable actualFieldValue;

    /**
     * Construct a {@code NoMatchingRelatedFieldsException} with the specified
     * expected and actual fields.
     *
     * @param expectedFieldName  the name used to label the expected field value
     * @param expectedFieldValue the expected field value
     * @param actualFieldName    the name used to label the violated field value
     * @param actualFieldValue   the violated field value
     */
    public NoMatchingRelatedFieldsException(
            final String expectedFieldName, final Serializable expectedFieldValue,
            final String actualFieldName, final Serializable actualFieldValue)
    {
        super(format("`%s` (%s) not matches `%s` (%s)",
                expectedFieldName, expectedFieldValue, actualFieldName, actualFieldValue));
        this.expectedFieldName = expectedFieldName;
        this.expectedFieldValue = expectedFieldValue;
        this.actualFieldName = actualFieldName;
        this.actualFieldValue = actualFieldValue;
    }

    /**
     * Construct a {@code NoMatchingRelatedFieldsException} with the specified
     * expected and actual fields with an optional root cause.
     *
     * @param expectedFieldName  the name used to label the expected field value
     * @param expectedFieldValue the expected field value
     * @param actualFieldName    the name used to label the violated field value
     * @param actualFieldValue   the violated field value
     * @param cause              the root cause. It can be {@code null}
     */
    public NoMatchingRelatedFieldsException(
            final String expectedFieldName, final Serializable expectedFieldValue,
            final String actualFieldName, final Serializable actualFieldValue,
            final @Nullable Throwable cause)
    {
        this(expectedFieldName, expectedFieldValue, actualFieldName, actualFieldValue);
        initCause(cause);
    }

    /**
     * Returns the name used to label the expected field value.
     *
     * @return the name used to label the expected field value
     */
    public String getExpectedFieldName()
    {
        return expectedFieldName;
    }

    /**
     * Returns the expected field value.
     *
     * @return the expected field value
     */
    public Serializable getExpectedFieldValue()
    {
        return expectedFieldValue;
    }

    /**
     * Returns the name used to label the violated field value.
     *
     * @return the name used to label the violated field value
     */
    public String getActualFieldName()
    {
        return actualFieldName;
    }

    /**
     * Returns the violated field value.
     *
     * @return the violated field value
     */
    public Serializable getActualFieldValue()
    {
        return actualFieldValue;
    }

    @Override
    public void populate(
            final @NonNull Environment environment,
            final @NonNull Map<String, Object> attributes)
    {
        attributes.put("expectedField", expectedFieldName);
        attributes.put("expectedValue", expectedFieldValue);
        attributes.put("actualField", actualFieldName);
        attributes.put("actualValue", actualFieldValue);
    }
}
