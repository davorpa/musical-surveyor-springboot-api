package io.davorpatech.fwk.auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Implementation that are aware of the application's current auditor based on
 * a fixed value.
 */
public class FixedStringAuditorAware implements AuditorAware<String> // NOSONAR
{
    private final String auditorName;

    /**
     * Constructs a new {@link FixedStringAuditorAware} with given arguments.
     *
     * @param auditorName the value used as fixed string.
     */
    public FixedStringAuditorAware(final String auditorName) {
        this.auditorName = auditorName;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(auditorName);
    }
}
