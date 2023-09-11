package io.davorpatech.fwk.auditing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for an auditing info.
 */
@ConfigurationProperties(prefix = "spring.data.auditing", ignoreUnknownFields = true)
public class SpringDataAuditingProperties
{
    /**
     * The auditor name used as fallback or default value.
     *
     * Defaults to {@code system}.
     */
    private String auditorName = "system";

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }
}
