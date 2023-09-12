package io.davorpatech.fwk.web.servlet.error.attributes.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the configuration of the extended error attributes context.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
        ExtensibleErrorAttributesConfiguration.class,
        ErrorAttributesCustomizerConfiguration.class
})
public @interface EnableExtensibleErrorAttributes
{
}
