package io.davorpatech.apps.musicalsurveyor.domain;

/**
 * Enumeration of the possible states of a raffle.
 */
public enum RaffleStatus {
    /**
     * The raffle is pending to be configured.
     *
     * <p>It is the initial state of a raffle. All raffles are created
     * using this state.
     *
     * <p>In this state, the raffle can be configured with the prizes
     * to be drawn.
     */
    PENDING,

    /**
     * The raffle is currently running.
     *
     * <p>In this state, the raffle is started and the prizes are locked.
     *
     * <p>Once the raffle is started, their configuration cannot be changed.
     */
    RUNNING,

    /**
     * The raffle has concluded.
     *
     * <p>This is the final state of a raffle.
     *
     * <p>In this state, the raffle is finished and the prizes has been assigned
     * to their respective winners.
     *
     * <p>Once the raffle is finished, it freezes and enters a read-only mode.
     */
    RESOLVED
}
