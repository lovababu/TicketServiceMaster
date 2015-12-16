package com.walmart.ticketmaster.service;

import com.walmart.ticketmaster.config.AppConfig;
import com.walmart.ticketmaster.constant.VenueLevelEnum;
import com.walmart.ticketmaster.domain.SeatHold;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by mkambam on 12/16/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    int expectedSeats = VenueLevelEnum.ORCHESTRA.getTotalSeats()
            + VenueLevelEnum.MAIN.getTotalSeats()
            + VenueLevelEnum.BALCONY1.getTotalSeats()
            + VenueLevelEnum.BALCONY2.getTotalSeats();
    @Test
    public void testNumSeatAvailable() {
        assertNotNull(ticketService);
        int seats = ticketService.numSeatsAvailable(Optional.<Integer>empty());
        assertEquals(expectedSeats, seats);
    }

    @Test
    public void testFindAndHoldSeats() {
        SeatHold seatHold = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "maheshk@gmail.com");
        assertNotNull(seatHold);
        assertNotNull(seatHold.getId());

        int count = ticketService.numSeatsAvailable(Optional.of(1));
        assertEquals(VenueLevelEnum.ORCHESTRA.getTotalSeats()-3, count);
    }

    @Test
    public void reserveSeats() {
        SeatHold seatHold = ticketService.findAndHoldSeats(4, Optional.of(2), Optional.of(4), "maheshk@gmail.com");
        assertNotNull(seatHold);
        assertNotNull(seatHold.getId());

        String string = ticketService.reserveSeats(seatHold.getId(), seatHold.getMailId());
        assertEquals("Your seats has been reserved.", string);

        int count = ticketService.numSeatsAvailable(Optional.of(2));
        assertEquals(VenueLevelEnum.MAIN.getTotalSeats() - 4, count);
    }
}
