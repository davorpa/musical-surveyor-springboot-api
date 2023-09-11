package io.davorpatech.fwk.validation;

import jakarta.validation.Validator;
import org.aopalliance.aop.Advice;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.function.Supplier;

/**
 * A convenient {@link BeanPostProcessor} implementation that delegates to a
 * JSR-303 provider for performing method-level validation on annotated methods.
 *
 * <p>Applicable methods have JSR-303 constraint annotations on their parameters
 * and/or on their return value (in the latter case specified at the method level,
 * typically as inline annotation), e.g.:
 *
 * <pre class="code">
 * public @NotNull Object myValidMethod(@NotNull String arg1, @Max(10) int arg2)
 * </pre>
 *
 * <p>Target classes with such annotated methods need to be annotated with
 * Spring's {@link Validated} annotation at the type level, for their methods to
 * be searched for inline constraint annotations. Validation groups can be
 * specified through {@code @Validated} as well. By default, JSR-303 will validate
 * against its default group only.
 *
 * <p>To add additional validation groups {@link ValidatedGroups} can be used as
 * annotation at specific method:
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
 * @see MethodValidationInterceptor
 * @see ValidatedGroupsAwareMethodValidationInterceptor
 * @see jakarta.validation.executable.ExecutableValidator
 */
public class ValidatedGroupsAwareMethodValidationPostProcessor
        extends MethodValidationPostProcessor
{
    /**
     * Create AOP advice for method validation purposes, to be applied
     * with a pointcut for the specified '{@link ValidatedGroups}' annotation.
     *
     * @param validator the JSR-303 Validator to delegate to
     * @return the interceptor to use (typically, but not necessarily,
     *         a {@link ValidatedGroupsAwareMethodValidationInterceptor}
     *         or subclass thereof)
     */
    @Override
    protected Advice createMethodValidationAdvice(final Supplier<Validator> validator) {
        return new ValidatedGroupsAwareMethodValidationInterceptor(validator);
    }
}
