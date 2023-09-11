package io.davorpatech.fwk.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation that are aware of the application's current auditor based on
 * the current {@link Authentication#getName() authentication name} of the
 * {@link SecurityContext} provided by Spring Security.
 */
public class SecurityContextPrincipalNameAuditorAware implements AuditorAware<String> // NOSONAR
{
    private final String fallbackAuditorName;

    /**
     * Constructs a new {@link SecurityContextPrincipalNameAuditorAware} with given
     * arguments.
     *
     * @param fallbackAuditorName the value used as fallback when no authentication
     *                            can be resolved.
     */
    public SecurityContextPrincipalNameAuditorAware(
            final String fallbackAuditorName) {
        this.fallbackAuditorName = fallbackAuditorName;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .or(this::getDefaultAuditor);
    }

    /**
     * Returns the fallback auditor of the application used when no authentication
     * can be resolved.
     *
     * @return the default auditor name
     */
    protected Optional<String> getDefaultAuditor() {
        return Optional.ofNullable(fallbackAuditorName);
    }
}
