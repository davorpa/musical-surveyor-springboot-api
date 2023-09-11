package io.davorpatech.fwk.validation;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * An AOP Alliance {@link MethodInterceptor} implementation that delegates to a
 * JSR-303 provider for performing method-level validation on annotated methods.
 *
 * <p>Applicable methods have JSR-303 constraint annotations on their parameters
 * and/or on their return value (in the latter case specified at the method level,
 * typically as inline annotation).
 *
 * <p>E.g.: {@code public @NotNull Object myValidMethod(@NotNull String arg1, @Max(10) int arg2)}
 *
 * <p>Validation groups can be specified through Spring's {@link Validated} annotation
 * at the type level of the containing target class, applying to all public service methods
 * of that class. By default, JSR-303 will validate against its default group only.
 *
 * <p>To add additional validation groups {@link ValidatedGroups} can be used as
 * annotation at specific method.
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
 * <p>As of Spring 5.0, this functionality requires a Bean Validation 1.1+ provider.
 *
 * @see ValidatedGroupsAwareMethodValidationPostProcessor
 * @see jakarta.validation.executable.ExecutableValidator
 */
public class ValidatedGroupsAwareMethodValidationInterceptor
        extends MethodValidationInterceptor
{
    /**
     * Create a new ValidatedGroupsAwareMethodValidationInterceptor using
     * a default JSR-303 validator underneath.
     */
    public ValidatedGroupsAwareMethodValidationInterceptor() {
        super();
    }

    /**
     * Create a new ValidatedGroupsAwareMethodValidationInterceptor using
     * the given JSR-303 ValidatorFactory.
     *
     * @param validatorFactory the JSR-303 ValidatorFactory to use
     */
    public ValidatedGroupsAwareMethodValidationInterceptor(
            final ValidatorFactory validatorFactory) {
        super(validatorFactory);
    }

    /**
     * Create a new MethodValidationInterceptor using the given JSR-303
     * Validator.
     *
     * @param validator the JSR-303 Validator to use
     */
    public ValidatedGroupsAwareMethodValidationInterceptor(
            final Validator validator) {
        super(validator);
    }

    /**
     * Create a new MethodValidationInterceptor for the supplied
     * (potentially lazily initialized) Validator.
     * @param validator a Supplier for the Validator to use
     */
    public ValidatedGroupsAwareMethodValidationInterceptor(
        final Supplier<Validator> validator) {
        super(validator);
    }

    /**
     * Determine the validation groups to validate against for the given
     * method invocation.
     *
     * <p>Default are the validation groups as specified in the {@link Validated}
     * annotation on the containing target class of the method. These groups are
     * merged with, {@link ValidatedGroups} if any.
     *
     * @param invocation the current MethodInvocation
     * @return the applicable validation groups as a Class array
     */
    @Override
    protected Class<?>[] determineValidationGroups(
            final MethodInvocation invocation)
    {
        final Class<?>[] classLevelGroups = super.determineValidationGroups(invocation);

        final ValidatedGroups validatedGroupsAnn = AnnotationUtils.findAnnotation(
                invocation.getMethod(), ValidatedGroups.class);

        final Class<?>[] methodLevelGroups = validatedGroupsAnn != null
                ? validatedGroupsAnn.value() : new Class<?>[0];
        if (methodLevelGroups.length == 0) {
            return classLevelGroups;
        }

        final Class<?>[] mergedGroups = Arrays.copyOf(
                classLevelGroups,
                classLevelGroups.length + methodLevelGroups.length);
        System.arraycopy(
                methodLevelGroups, 0, mergedGroups,
                classLevelGroups.length, methodLevelGroups.length);

        return mergedGroups;
    }
}
