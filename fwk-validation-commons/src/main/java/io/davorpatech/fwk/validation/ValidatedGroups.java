package io.davorpatech.fwk.validation;

import java.lang.annotation.*;


/**
 * Variant of JSR-303's {@link jakarta.validation.Valid}, supporting the
 * specification of additional validation groups along with
 * {@link org.springframework.validation.annotation.Validated}. Designed
 * for convenient use with Spring's JSR-303 support but not JSR-303 specific.
 *
 * <p>Can be used e.g. with Spring MVC handler methods arguments.
 * Supported through {@link org.springframework.validation.SmartValidator}'s
 * validation hint concept, with validation group classes acting as hint objects.
 *
 * <p>Can also be used with method level validation, indicating that a specific
 * class is supposed to be validated at the method level (acting as a pointcut
 * for the corresponding validation interceptor), but also optionally specifying
 * the validation groups for method-level validation in the annotated class.
 * Sometimes you have a class containing several methods with the same entity
 * as a parameter but each of which requiring different subset of properties
 * to validate. Applying this annotation at the method level allows for add
 * additional validation groups for a specific method but does not serve as
 * a pointcut; a class-level annotation is nevertheless necessary to trigger
 * method validation for a specific bean to begin with. Can also be used as a
 * meta-annotation on a custom stereotype annotation or a custom group-specific
 * validated annotation.
 *
 * <p>Typically used as:
 *
 * <pre>{@code
 * @Validated(groups = Group1.class)
 * public class MyClass {
 *
 *     @ValidatedGroups(Group2.class)
 *     public myMethod1(Foo foo) { ... }
 *
 *     public myMethod2(Foo foo) { ... }
 *
 *     ...
 * }
 * }</pre>
 *
 * @see org.springframework.validation.annotation.Validated
 * @see jakarta.validation.Validator#validate(Object, Class[])
 * @see org.springframework.validation.SmartValidator#validate(Object, org.springframework.validation.Errors, Object...)
 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter
 * @see io.davorpatech.fwk.validation.ValidatedGroupsAwareMethodValidationPostProcessor
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidatedGroups
{
    /**
     * Specify one or more additional validation groups to apply
     * to the validation step kicked off by this annotation.
     *
     * <p>JSR-303 defines validation groups as custom annotations
     * which an application declares for the sole purpose of using
     * them as type-safe group arguments, as implemented in
     * {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter}.
     *
     * <p>Other {@link org.springframework.validation.SmartValidator} implementations may
     * support class arguments in other ways as well.
     */
    Class<?>[] value() default {};
}
