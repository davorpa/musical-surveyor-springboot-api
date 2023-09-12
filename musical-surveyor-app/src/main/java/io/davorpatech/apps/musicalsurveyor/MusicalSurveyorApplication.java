package io.davorpatech.apps.musicalsurveyor;

import io.davorpatech.fwk.auditing.config.SpringDataAuditingConfiguration;
import io.davorpatech.fwk.validation.config.EnableValidatedGroups;
import io.davorpatech.fwk.web.servlet.error.attributes.config.EnableExtensibleErrorAttributes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@EnableValidatedGroups
@EnableExtensibleErrorAttributes
@Import({
    SpringDataAuditingConfiguration.class
})
@SpringBootApplication
public class MusicalSurveyorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicalSurveyorApplication.class, args);
    }

}
