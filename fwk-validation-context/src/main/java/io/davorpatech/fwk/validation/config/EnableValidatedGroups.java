package io.davorpatech.fwk.validation.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the processing of the {@link io.davorpatech.fwk.validation.ValidatedGroups
 * ValidatedGroups} validation annotation.
 *
 * <p>A custom {@link org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 * MethodValidationPostProcessor} is registered into the Spring bean's context
 * to process this validation annotation.
 *
 * @see io.davorpatech.fwk.validation.ValidatedGroups ValidatedGroups
 * @see ValidatedGroupsConfiguration
 * @see io.davorpatech.fwk.validation.ValidatedGroupsAwareMethodValidationPostProcessor
 *      ValidatedGroupsAwareMethodValidationPostProcessor
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
        ValidatedGroupsConfiguration.class
})
public @interface EnableValidatedGroups
{

}
