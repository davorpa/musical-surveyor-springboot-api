package io.davorpatech.apps.musicalsurveyor.domain.prizes;

/**
 * Constants to work with {@code Prize} domain entities and DTOs.
 */
public interface PrizeConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.Prize";

    /**
     * The maximum length of the {@code title} field.
     */
    int TITLE_MAXLEN = 255;

    /**
     * The maximum length of the {@code description} field.
     */
    int DESCRIPTION_MAXLEN = 2048;

    /**
     * The precision of the {@code monetaryValue} field.
     */
    int MONETARY_VALUE_PRECISION = 10;

    /**
     * The scale of the {@code monetaryValue} field.
     */
    int MONETARY_VALUE_SCALE = 2;

    /**
     * The minimum value of the {@code monetaryValue} field.
     */
    int MONETARY_VALUE_MIN = 0;
}
