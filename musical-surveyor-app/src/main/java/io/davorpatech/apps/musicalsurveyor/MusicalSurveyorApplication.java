package io.davorpatech.apps.musicalsurveyor;

import io.davorpatech.fwk.auditing.config.SpringDataAuditingConfiguration;
import io.davorpatech.fwk.validation.config.EnableValidatedGroups;
import io.davorpatech.fwk.web.servlet.error.attributes.config.EnableExtensibleErrorAttributes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The Musical Surveyor application class.
 *
 * <p>This class is the main entry point of the application.
 * It is annotated with {@link SpringBootApplication} to enable
 * auto-configuration and component scanning.
 *
 * <p>It also imports the {@link SpringDataAuditingConfiguration}
 * class to enable auditing on the application.
 *
 * <p>Finally, it enables the {@link EnableValidatedGroups} and
 * {@link EnableExtensibleErrorAttributes} annotations to enable
 * validation groups and extensible error attributes on the application.
 */
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
