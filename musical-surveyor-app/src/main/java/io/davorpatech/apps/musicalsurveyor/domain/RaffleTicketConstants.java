package io.davorpatech.apps.musicalsurveyor.domain;

/**
 * Constants to work with {@code RaffleTicket} domain entities and DTOs.
 */
public interface RaffleTicketConstants { // NOSONAR
    /**
     * The name of the domain entity.
     */
    String DOMAIN_NAME = "musicpoll.RaffleTicket";

    /**
     * The maximum length of the {@code number} field.
     */
    int NUMBER_MAXLEN = 40;

    /**
     * The regular expression to validate the {@code number} field.
     */
    String NUMBER_REGEX = "^[0-9a-z]+$";
}
