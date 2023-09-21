package io.davorpatech.apps.musicalsurveyor.domain.surveys;

/**
 * Constants to work with {@code Survey} domain entities and DTOs.
 */
public interface SurveyConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.Survey";

    /**
     * The maximum length of the {@code title} field.
     */
    int TITLE_MAXLEN = 255;

    /**
     * The maximum length of the {@code description} field.
     */
    int DESCRIPTION_MAXLEN = 2048;

    /**
     * The maximum length of the {@code status} field.
     */
    int STATUS_MAXLEN = 50;

    /**
     * The minimum value of the {@code numMaxParticipants} field.
     */
    int CFG_NUM_MAX_PARTICIPANTS_MIN = 0;

    /**
     * The minimum value of the {@code numResponses} field.
     */
    int CFG_NUM_RESPONSES_MIN = 0;
}
