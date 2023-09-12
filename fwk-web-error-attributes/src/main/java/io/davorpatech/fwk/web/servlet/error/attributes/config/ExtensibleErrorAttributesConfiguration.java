package io.davorpatech.fwk.web.servlet.error.attributes.config;

import io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer;
import io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes;
import jakarta.servlet.Servlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific {@link Configuration} to register into the Spring bean's context
 * those necessary beans to compose the error attributes which can be logged
 * or presented to the user.
 *
 * @see ErrorAttributesCustomizer
 * @see ExtensibleErrorAttributes
 * @see ErrorAttributes
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
public class ExtensibleErrorAttributesConfiguration
{
    @Autowired(required = false)
    private List<ErrorAttributesCustomizer> errorAttributesCustomizers = new ArrayList<>();

    @Bean
    @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
    public ExtensibleErrorAttributes errorAttributes()
    {
        return new ExtensibleErrorAttributes(errorAttributesCustomizers);
    }
}
