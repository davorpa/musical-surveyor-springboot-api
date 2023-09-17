package io.davorpatech.apps.musicalsurveyor.domain.artist;

/**
 * Constants to work with {@code Artist} domain entities and DTOs.
 */
public interface ArtistConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.Artist";

    /**
     * The maximum length of the {@code name} field.
     */
    int NAME_MAXLEN = 255;

    /**
     * The maximum length of the {@code biography} field.
     */
    int BIOGRAPHY_MAXLEN = 2048;
}
