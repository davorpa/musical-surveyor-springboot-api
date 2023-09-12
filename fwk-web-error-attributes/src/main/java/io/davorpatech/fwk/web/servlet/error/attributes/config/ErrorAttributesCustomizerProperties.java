package io.davorpatech.fwk.web.servlet.error.attributes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties}
 * for the error attributes customizers.
 */
@ConfigurationProperties(prefix = "server.error.customizers", ignoreUnknownFields = true)
public class ErrorAttributesCustomizerProperties
{
    /**
     * Configuration properties used to set up the timestamp error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.TimestampErrorAttributeAdder
     *      TimestampErrorAttributeAdder
     */
    private final Customizer timestamp = new Customizer(0, "timestamp");

    /**
     * Configuration properties used to set up the HTTP status error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.StatusErrorAttributeAdder
     *      StatusErrorAttributeAdder
     */
    private final Customizer status = new Customizer(200, "status");

    /**
     * Configuration properties used to set up the exception type error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.ExceptionTypeErrorAttributeAdder
     *      ExceptionTypeErrorAttributeAdder
     */
    private final Customizer exceptionType = new Customizer(300, "exception");

    /**
     * Configuration properties used to set up the exception/error message error
     * attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.ErrorMessageErrorAttributeAdder
     *      ErrorMessageErrorAttributeAdder
     */
    private final Customizer errorMessage = new Customizer(320, "message");

    /**
     * Configuration properties used to set up the error-code error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.ErrorCodeErrorAttributeAdder
     *      ErrorCodeErrorAttributeAdder
     */
    private final Customizer errorCode = new Customizer(340, "code");

    /**
     * Configuration properties used to set up domain entity name error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.ErrorDomainErrorAttributeAdder
     *      ErrorDomainErrorAttributeAdder
     */
    private final Customizer errorDomain = new Customizer(350, "domain");

    /**
     * Configuration properties used to set up the domain entity identifier error
     * attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.IdentifiableErrorAttributeAdder
     *      IdentifiableErrorAttributeAdder
     */
    private final Customizer identifiable = new Customizer(360, "identifier");

    /**
     * Configuration properties used to set up the additional arguments error
     * attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.ErrorArgumentsErrorAttributeAdder
     *      ErrorArgumentsErrorAttributeAdder
     */
    private final Customizer argsPopulate = new Customizer(370, "arguments");

    /**
     * Configuration properties used to set up the binding result error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.BindingResultErrorAttributesAdder
     *      BindingResultErrorAttributesAdder
     */
    private final Customizer bindingErrors = new Customizer(380, "errors");

    /**
     * Configuration properties used to set up the exception stacktrace error
     * attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.ExceptionTraceErrorAttributeAdder
     *      ExceptionTraceErrorAttributeAdder
     */
    private final Customizer stacktrace = new Customizer(399, "trace");

    /**
     * Configuration properties used to set up the path error attribute.
     *
     * @see io.davorpatech.fwk.web.servlet.error.attributes.customizers.PathErrorAttributeAdder
     *      PathErrorAttributeAdder
     */
    private final Customizer path = new Customizer(999, "path");

    /**
     * Gets the configuration properties used to set up the timestamp
     * error attribute customizer.
     *
     * @return the timestamp error attribute customizer configuration properties
     */
    public Customizer getTimestamp()
    {
        return timestamp;
    }

    /**
     * Gets the configuration properties used to set up the HTTP status
     * error attribute customizer.
     *
     * @return the HTTP status error attribute customizer configuration
     *         properties
     */
    public Customizer getStatus()
    {
        return status;
    }

    /**
     * Gets the configuration properties used to set up the exception type
     * error attribute customizer.
     *
     * @return the exception type error attribute customizer configuration
     *         properties
     */
    public Customizer getExceptionType()
    {
        return exceptionType;
    }

    /**
     * Gets the configuration properties used to set up the exception/error message
     * error attribute customizer.
     *
     * @return the exception/error message error attribute customizer configuration
     *         properties
     */
    public Customizer getErrorMessage()
    {
        return errorMessage;
    }

    /**
     * Gets the configuration properties used to set up the human-friendly error
     * code error attribute customizer.
     *
     * @return the human-friendly error code error attribute customizer configuration
     *         properties
     */
    public Customizer getErrorCode()
    {
        return errorCode;
    }

    /**
     * Gets the configuration properties used to set up the domain entity name
     * error attribute customizer.
     *
     * @return the domain entity name error attribute customizer configuration
     *         properties
     */
    public Customizer getErrorDomain()
    {
        return errorDomain;
    }

    /**
     * Gets the configuration properties used to set up the domain entity identifier
     * error attribute customizer.
     *
     * @return the domain entity identifier error attribute customizer configuration
     *         properties
     */
    public Customizer getIdentifiable()
    {
        return identifiable;
    }

    /**
     * Gets the configuration properties used to set up the additional arguments
     * error attribute customizer.
     *
     * @return the additional arguments error attribute customizer configuration
     *         properties
     */
    public Customizer getArgsPopulate()
    {
        return argsPopulate;
    }

    /**
     * Gets the configuration properties used to set up the binding result error
     * attribute customizer.
     *
     * @return the binding result error attribute customizer configuration properties
     */
    public Customizer getBindingErrors()
    {
        return bindingErrors;
    }

    /**
     * Gets the configuration properties used to set up the exception stacktrace error
     * attribute customizer.
     *
     * @return the exception stacktrace error attribute customizer configuration
     *         properties
     */
    public Customizer getStacktrace()
    {
        return stacktrace;
    }

    /**
     * Gets the configuration properties used to set up the path error attribute
     * customizer.
     *
     * @return the path error attribute customizer configuration properties
     */
    public Customizer getPath()
    {
        return path;
    }


    public static class Customizer
    {
        /**
         * Whether to enable the customizer.
         */
        private boolean enabled = true;

        /**
         * The order used to sort the error attribute customizers.
         */
        private int order;

        /**
         * The object attribute path in where put the error attribute value resolved
         * by the customizer.
         *
         * <p>A valid path is defined by one or more words separated with dots
         * (e.g. {@code "acme.system.feature"}).
         */
        private String attrPath;

        /**
         * Constructs a new {@link Customizer} configuration properties node.
         *
         * @param order    the order used to sort the error attribute customizers.
         * @param attrPath the object attribute path in where put the error attribute
         *                 value resolved by the customizer.
         */
        public Customizer(
                final int order,
                final String attrPath)
        {
            this.order = order;
            this.attrPath = attrPath;
        }

        /**
         * Finds out if the customizer is enabled.
         *
         * <p>The state by default is always enabled.
         *
         * @return {@code true} if enabled
         */
        public boolean isEnabled()
        {
            return enabled;
        }

        /**
         * Set the enabled state of the customizer.
         *
         * @param enabled the enabled state of the customizer, {@code true} to enable.
         */
        public void setEnabled(final boolean enabled)
        {
            this.enabled = enabled;
        }

        /**
         * Gets the order used to sort the error attribute customizers.
         *
         * @return the order used to sort the error attribute customizers.
         */
        public int getOrder()
        {
            return order;
        }

        /**
         * Sets the order used to sort the error attribute customizers.
         *
         * @param order the order to set
         */
        public void setOrder(final int order)
        {
            this.order = order;
        }

        /**
         * Gets the object attribute path in where put the error attribute value resolved
         * by the customizer.
         *
         * <p>A valid path is defined by one or more words separated with dots
         * (e.g. {@code "acme.system.feature"}).
         *
         * @return the attribute path
         */
        public String getAttrPath()
        {
            return attrPath;
        }

         /**
         * Sets the object attribute path in where put the error attribute value resolved
         * by the customizer.
         *
         * <p>A valid path is defined by one or more words separated with dots
         * (e.g. {@code "acme.system.feature"}).
         *
         * @param attrPath the attribute path to set
         */
        public void setAttrPath(final String attrPath)
        {
            this.attrPath = attrPath;
        }
    }
}
