package io.davorpatech.apps.musicalsurveyor.domain.surveys;

/**
 * Enumeration of the possible states of a survey.
 */
public enum SurveyStatus {
    /**
     * The survey is pending to be configured.
     *
     * <p>It is the initial state of a survey. All surveys are created
     * using this state.
     *
     * <p>In this state, the survey can be configured:
     * <ul>
     * <li>the start and end dates
     * <li>the participants, but only the first time.
     * <li>the configuration parameters
     * </ul>
     */
    PENDING,

    /**
     * The survey is currently running.
     *
     * <p>In this state, the start date has past and some participants have
     * summited their respective responses.
     *
     * <p>Once the survey is started, their configuration cannot be changed,
     * thus is:
     * <ul>
     * <li>not possible to change the start date
     * <li>not possible to manage their participants.
     * <li>not possible to change the configuration parameters
     * </ul>
     */
    RUNNING,

    /**
     * The survey has concluded.
     *
     * <p>This is the final state of a survey.
     *
     * <p>In this state, the survey is finished and all participant responses
     * has been assigned to their respective participants or the administrator
     * has manually closed the survey if the end date has past.
     *
     * <p>Once the survey is finished, it freezes and enters a read-only mode.
     * Also, no more responses can be summited.
     *
     * <p>With this mode the administrator gain access to create raffles.
     */
    CLOSED
}
