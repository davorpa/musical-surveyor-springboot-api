package io.davorpatech.apps.musicalsurveyor.domain.colors;

/**
 * Constants to work with {@code Color} domain entities and DTOs.
 */
public interface ColorConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.Color";

    /**
     * The maximum length of the {@code code} field.
     */
    int CODE_MAXLEN = 25;

    /**
     * The regular expression to validate the {@code code} field.
     */
    String CODE_REGEX = "^(\\#[0-9A-F]{6})$|^([a-z]+)$";
}
