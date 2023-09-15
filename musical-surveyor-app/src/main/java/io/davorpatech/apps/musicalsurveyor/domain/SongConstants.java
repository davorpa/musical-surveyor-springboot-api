package io.davorpatech.apps.musicalsurveyor.domain;

/**
 * Constants to work with {@code Song} domain entities and DTOs.
 */
public interface SongConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.Song";

    /**
     * The maximum length of the {@code title} field.
     */
    int TITLE_MAXLEN = 255;

    /**
     * The minimum value of the {@code year} field.
     */
    int YEAR_MIN = 0;

    /**
     * The minimum value of the {@code duration} field.
     */
    int DURATION_MIN = 0;

    /**
     * The maximum length of the {@code genre} field.
     */
    int GENRE_MAXLEN = 50;
}
