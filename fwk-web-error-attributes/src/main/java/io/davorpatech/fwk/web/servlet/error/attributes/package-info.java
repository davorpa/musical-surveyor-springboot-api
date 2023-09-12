/**
 * Exposing the errors captured within an application to the outside following
 * a style guide is an important aspect of them.
 *
 * <p>This package define those infrastructure that allows this process in a
 * configurable and non-intrusive way. This is done by registering, as small
 * pieces, a set of customizers dedicated to process each attributes of a given
 * error context but depending on a series of configuration options.
 *
 *
 * <h2><a id="usage">Usage</a></h2>
 *
 * <p>To use this featured component, annotate every Spring Boot Application with
 * {@code @EnableExtensibleErrorAttributes} annotation. That's it.
 *
 * <pre>{@code
 * @EnableExtensibleErrorAttributes
 * @SpringBootApplication
 * public class Application {
 *
 * }
 * }</pre>
 *
 * <p>The annotation will auto register the
 * {@link io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 * ErrorAttributes bean} in charge of customize an error attributes map context.
 * Besides that, a set of built-in
 * {@link io.davorpatech.fwk.web.servlet.error.attributes.ErrorAttributesCustomizer
 * customizers} are also added to cover each default attribute.
 *
 *
 * <h2><a id="configuration">Configuration Options</a></h2>
 *
 * <p>For each customizer, you can configure following options:
 * <ul>
 * <li>{@code enabled} - if
 * {@link io.davorpatech.fwk.web.servlet.error.attributes.ExtensibleErrorAttributes
 * error attributes bean} should take into account as property to process.
 * All customizers are enabled by default.
 * <li>{@code order} - The sort order used to process the customizer by the error
 * attributes bean. Higher values means lower precedence.
 * <li>{@code attr-path} - the property path into error attributes map where in
 * put the its value value resolved by the customizer. A valid path is defined by
 * one or more words separated with dots (e.g. {@code "acme.system.feature"}).
 * </ul>
 *
 * <p>Default {@code application.properties} (see
 * {@link io.davorpatech.fwk.web.servlet.error.attributes.config.ErrorAttributesCustomizerProperties
 * ErrorAttributesCustomizerProperties}):
 *
 * <pre>{@code
 * ## Customizer configuration of the timestamp error attribute
 * server.error.customizers.timestamp.enabled=true
 * server.error.customizers.timestamp.order=0
 * server.error.customizers.timestamp.attr-path=timestamp
 * ## Customizer configuration of the request correlation error attribute
 * server.error.customizers.correlation.enabled=true
 * server.error.customizers.correlation.order=100
 * server.error.customizers.correlation.attr-path=correlation
 * ## Customizer configuration of the HTTP status error attribute
 * server.error.customizers.status.enabled=true
 * server.error.customizers.status.order=200
 * server.error.customizers.status.attr-path=status
 * ## Customizer configuration of the exception type name error attribute
 * server.error.customizers.exception-type.enabled=true
 * server.error.customizers.exception-type.order=300
 * server.error.customizers.exception-type.attr-path=error.exception
 * ## Customizer configuration of the exception/error message error attribute
 * server.error.customizers.error-message.enabled=true
 * server.error.customizers.error-message.order=320
 * server.error.customizers.error-message.attr-path=error.message
 * ## Customizer configuration of the human-friendly error code error attribute
 * server.error.customizers.error-code.enabled=true
 * server.error.customizers.error-code.order=340
 * server.error.customizers.error-code.attr-path=error.code
 * ## Customizer configuration of the human-friendly domain entity error attribute
 * server.error.customizers.error-domain.enabled=true
 * server.error.customizers.error-domain.order=350
 * server.error.customizers.error-domain.attr-path=error.domain
 * ## Customizer configuration of the identifiable error attribute
 * server.error.customizers.identifiable.enabled=true
 * server.error.customizers.identifiable.order=360
 * server.error.customizers.identifiable.attr-path=error.identifier
 * ## Customizer configuration of the additional arguments error attribute
 * server.error.customizers.args-populate.enabled=true
 * server.error.customizers.args-populate.order=370
 * server.error.customizers.args-populate.attr-path=error.arguments
 * ## Customizer configuration of the binding results error attribute
 * server.error.customizers.binding-errors.enabled=true
 * server.error.customizers.binding-errors.order=380
 * server.error.customizers.binding-errors.attr-path=error.errors
 * ## Customizer configuration of the stacktrace error attribute
 * server.error.customizers.stacktrace.enabled=true
 * server.error.customizers.stacktrace.order=399
 * server.error.customizers.stacktrace.attr-path=error.trace
 * ## Customizer configuration of the path error attribute
 * server.error.customizers.path.enabled=true
 * server.error.customizers.path.order=999
 * server.error.customizers.path.attr-path=path
 * }</pre>
 *
 *
 * <h2><a id="default-attributes">Default attributes</a></h2>
 *
 * <p>As base, the following attributes are provided into the error context when
 * possible:
 * <ul>
 * <li>{@code timestamp} - The time that the errors were extracted.
 * <li>{@code correlation} - The unique request correlation
 * ({@link io.davorpatech.fwk.web.request.correlation.RequestCorrelation}) that
 * identifies current HTTP request (if configured).
 * <li>{@code status.code} - The status code.
 * <li>{@code status.reason} - The status reason phrase.
 * <li>{@code error.exception} - The class name of the root exception (if
 * configured).
 * <li>{@code error.message} - The exception message (if configured).
 * <li>{@code error.errors} - Any {@link org.springframework.validation.ObjectError}s
 * from a {@link org.springframework.validation.BindingResult} exception (if
 * configured and provided by the exception).
 * <li>{@code error.trace} - The exception stack trace (if configured).
 * <li>{@code error.code} - An optional property indicating the human-friendly
 * error code provided by any exception marked with
 * {@link io.davorpatech.fwk.model.ErrorCode}.
 * <li>{@code error.domain} - An optional property indicating the human-friendly
 * domain entity name provided by any exception marked with
 * {@link io.davorpatech.fwk.model.ErrorDomain}.
 * <li>{@code error.identifier} - An optional property with the value/s that
 * identify the domain entity which the error is related for. Provided by any
 * exception marked with {@link io.davorpatech.fwk.model.Identifiable}.
 * <li>{@code error.arguments} - An optional property with the additional
 * attributes populated by any exception marked with
 * {@link io.davorpatech.fwk.model.AdditionalArgumentsPopulator}.
 * <li>{@code path} - The URL path when the exception was raised.
 * </ul>
 */
package io.davorpatech.fwk.web.servlet.error.attributes;
