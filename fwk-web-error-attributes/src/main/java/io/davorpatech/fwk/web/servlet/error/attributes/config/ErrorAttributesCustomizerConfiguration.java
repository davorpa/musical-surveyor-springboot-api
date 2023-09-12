package io.davorpatech.fwk.web.servlet.error.attributes.config;

import io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes;
import io.davorpatech.fwk.web.servlet.error.attributes.customizers.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

/**
 * Specific {@link Configuration} to register into the Spring bean's context
 * those necessary built-in beans in charge of compose the default error attributes
 * which can be logged or presented to the user.
 *
 * @see io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 *      ErrorAttributesCustomizer
 */
@Configuration
@ConditionalOnBean(ExtensibleErrorAttributes.class)
@EnableConfigurationProperties(ErrorAttributesCustomizerProperties.class)
public class ErrorAttributesCustomizerConfiguration
{
    @Bean
    @ConditionalOnMissingBean(value = TimestampErrorAttributeAdder.class,search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.timestamp.enabled", matchIfMissing = true)
    public TimestampErrorAttributeAdder timestampErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new TimestampErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = PathErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.path.enabled", matchIfMissing = true)
    public PathErrorAttributeAdder pathErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new PathErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = StatusErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.status.enabled", matchIfMissing = true)
    public StatusErrorAttributeAdder statusErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new StatusErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = ExceptionTypeErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.exception-type.enabled", matchIfMissing = true)
    public ExceptionTypeErrorAttributeAdder exceptionTypeErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new ExceptionTypeErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorMessageErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.error-message.enabled", matchIfMissing = true)
    public ErrorMessageErrorAttributeAdder errorMessageErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new ErrorMessageErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = BindingResultErrorAttributesAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.binding-errors.enabled", matchIfMissing = true)
    public BindingResultErrorAttributesAdder bindingResultErrorAttributesAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new BindingResultErrorAttributesAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = ExceptionTraceErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.stacktrace.enabled", matchIfMissing = true)
    public ExceptionTraceErrorAttributeAdder exceptionTraceErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new ExceptionTraceErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorArgumentsErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.args-populate.enabled", matchIfMissing = true)
    public ErrorArgumentsErrorAttributeAdder errorArgumentsErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties,
            final @NonNull Environment environment)
    {
        return new ErrorArgumentsErrorAttributeAdder(customizerProperties, environment);
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorCodeErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.error-code.enabled", matchIfMissing = true)
    public ErrorCodeErrorAttributeAdder errorCodeErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new ErrorCodeErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorDomainErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.error-domain.enabled", matchIfMissing = true)
    public ErrorDomainErrorAttributeAdder errorDomainErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new ErrorDomainErrorAttributeAdder(customizerProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = IdentifiableErrorAttributeAdder.class, search = SearchStrategy.CURRENT)
    @ConditionalOnProperty(value = "server.error.customizers.identifiable.enabled", matchIfMissing = true)
    public IdentifiableErrorAttributeAdder identifiableErrorAttributeAdder(
            final @NonNull ErrorAttributesCustomizerProperties customizerProperties)
    {
        return new IdentifiableErrorAttributeAdder(customizerProperties);
    }
}
