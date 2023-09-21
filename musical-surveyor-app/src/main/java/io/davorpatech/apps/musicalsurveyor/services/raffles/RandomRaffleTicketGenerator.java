package io.davorpatech.apps.musicalsurveyor.services.raffles;

import io.davorpatech.apps.musicalsurveyor.persistence.dao.ColorRepository;
import io.davorpatech.apps.musicalsurveyor.persistence.model.Color;
import io.davorpatech.apps.musicalsurveyor.persistence.model.RaffleTicket;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Generates random raffle tickets.
 */
@Component
public class RandomRaffleTicketGenerator implements RaffleTicketGenerator {

    private final ColorRepository colorRepository;

    /**
     * Creates a new instance of {@link RandomRaffleTicketGenerator}.
     *
     * @param colorRepository must not be {@literal null}.
     */
    public RandomRaffleTicketGenerator(ColorRepository colorRepository) {
        Assert.notNull(colorRepository, "ColorRepository must not be null!");
        this.colorRepository = colorRepository;
    }

    @Override
    public RaffleTicket generate() {
        RaffleTicket ticket = new RaffleTicket();
        ticket.setColor(generateRandomColor());
        ticket.setNumber(generateRandomSerialNumber());
        return ticket;
    }

    /**
     * Generates a random color.
     *
     * @return a random color.
     */
    protected Color generateRandomColor() {
        List<Color> colors = colorRepository.findAll();
        int index = ThreadLocalRandom.current().nextInt(colors.size());
        return colors.get(index);
    }

    /**
     * Generates a random serial number.
     *
     * @return a random serial number.
     */
    protected String generateRandomSerialNumber() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
