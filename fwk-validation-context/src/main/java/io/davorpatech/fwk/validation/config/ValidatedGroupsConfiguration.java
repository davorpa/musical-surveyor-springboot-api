package io.davorpatech.fwk.validation.config;

import io.davorpatech.fwk.validation.FilteredValidatedGroupsAwareMethodValidationPostProcessor;
import io.davorpatech.fwk.validation.ValidatedGroupsAwareMethodValidationPostProcessor;
import jakarta.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Specific {@link Configuration} to register into the Spring bean's
 * context those necessary beans to be able to handle the
 * {@link io.davorpatech.fwk.validation.ValidatedGroups ValidatedGroups}
 * validation annotation.
 *
 * @see io.davorpatech.fwk.validation.ValidatedGroups ValidatedGroups
 * @see ValidatedGroupsAwareMethodValidationPostProcessor
 * @see org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
 *      ValidationAutoConfiguration
 */
@Configuration
@ConditionalOnClass(ExecutableValidator.class)
public class ValidatedGroupsConfiguration
{
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(
            final Environment environment,
            final ObjectProvider<Validator> validator,
            final ObjectProvider<MethodValidationExcludeFilter> excludeFilters)
    {
        MethodValidationPostProcessor processor = excludeFilters == null
                ? new ValidatedGroupsAwareMethodValidationPostProcessor()
                : new FilteredValidatedGroupsAwareMethodValidationPostProcessor(
                        excludeFilters.orderedStream());
        boolean proxyTargetClass = environment.getProperty(
                "spring.aop.proxy-target-class", Boolean.class, true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidatorProvider(validator);
        return processor;
    }
}
