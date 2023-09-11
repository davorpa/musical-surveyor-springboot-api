package io.davorpatech.fwk.auditing.config;

import io.davorpatech.fwk.auditing.FixedStringAuditorAware;
import io.davorpatech.fwk.auditing.SecurityContextPrincipalNameAuditorAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.Assert;

/**
 * Configures the Spring Data auditing capabilities.
 */
@Configuration
@EnableConfigurationProperties(SpringDataAuditingProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareProvider")
public class SpringDataAuditingConfiguration
{
    private final SpringDataAuditingProperties properties;

    public SpringDataAuditingConfiguration(
            final SpringDataAuditingProperties properties) {
        Assert.notNull(properties, "SpringDataAuditingProperties must not be null!");
        this.properties = properties;
    }

    @Bean("auditorAwareProvider")
    @ConditionalOnMissingClass("org.springframework.security.core.context.SecurityContext")
    public AuditorAware<String> fixedStringAuditorAware() {
        return new FixedStringAuditorAware(properties.getAuditorName());
    }

    @Bean("auditorAwareProvider")
    @ConditionalOnClass(name = "org.springframework.security.core.context.SecurityContext")
    public AuditorAware<String> securityContextPrincipalNameAuditorAware() {
        return new SecurityContextPrincipalNameAuditorAware(properties.getAuditorName());
    }
}
