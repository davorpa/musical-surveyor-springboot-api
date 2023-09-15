package io.davorpatech.apps.musicalsurveyor.domain;

/**
 * Constants to work with {@code RadioListener} domain entities and DTOs.
 */
public interface RadioListenerConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.RadioListener";

    /**
     * The maximum length of the {@code name} field.
     */
    int NAME_MAXLEN = 255;

    /**
     * The maximum length of the {@code phone} field.
     */
    int PHONE_MAXLEN = 15;

    /**
     * The regular expression to validate the {@code phone} field.
     */
    String PHONE_REGEX = "^\\+[0-9]{9,}$"; // Example: +34659881420

    /**
     * The maximum length of the {@code email} field.
     */
    int EMAIL_MAXLEN = 255;

    /**
     * The maximum length of the {@code address} field.
     */
    int ADDRESS_MAXLEN = 500;
}
