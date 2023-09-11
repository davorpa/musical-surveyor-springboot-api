package io.davorpatech.fwk.validation;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.validation.beanvalidation.FilteredMethodValidationPostProcessor;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom {@link ValidatedGroupsAwareMethodValidationPostProcessor} that applies
 * {@link MethodValidationExcludeFilter exclusion filters}.
 *
 * @see FilteredMethodValidationPostProcessor
 */
public class FilteredValidatedGroupsAwareMethodValidationPostProcessor
        extends ValidatedGroupsAwareMethodValidationPostProcessor
{
    private static final long serialVersionUID = -4704208323453715625L;

    private final Collection<MethodValidationExcludeFilter> excludeFilters; // NOSONAR

    /**
     * Creates a new {@code FilteredValidatedGroupsAwareMethodValidationPostProcessor}
     * that will apply the given {@code excludeFilters} when identifying beans that
     * are eligible for method validation post-processing.
     *
     * @param excludeFilters filters to apply
     */
    public FilteredValidatedGroupsAwareMethodValidationPostProcessor(
            final Stream<? extends MethodValidationExcludeFilter> excludeFilters)
    {
        this.excludeFilters = excludeFilters.collect(Collectors.toList()); // NOSONAR
    }

    /**
     * Creates a new {@code FilteredValidatedGroupsAwareMethodValidationPostProcessor}
     * that will apply the given {@code excludeFilters} when identifying beans that
     * are eligible for method validation post-processing.
     *
     * @param excludeFilters filters to apply
     */
    public FilteredValidatedGroupsAwareMethodValidationPostProcessor(
            final Collection<? extends MethodValidationExcludeFilter> excludeFilters)
    {
        this.excludeFilters = new ArrayList<>(excludeFilters);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        final DefaultPointcutAdvisor advisor = (DefaultPointcutAdvisor) this.advisor;
        final ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        final MethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();
        advisor.setPointcut(new ComposablePointcut(classFilter, methodMatcher)
                .intersection(this::isIncluded));
    }

    private boolean isIncluded(Class<?> candidate) {
        for (MethodValidationExcludeFilter exclusionFilter : this.excludeFilters) {
            if (exclusionFilter.isExcluded(candidate)) {
                return false;
            }
        }
        return true;
    }
}
