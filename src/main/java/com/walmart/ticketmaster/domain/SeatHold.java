package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by mkambam on 12/14/15.
 */
@Setter
@Getter
public class SeatHold {

    private final Integer id;

    private final String mailId;

    private final Long timer;

    private List<Seat> seats;


    public SeatHold(Integer seatHoldId, String customerMailId, Long durationToHold) {
        id = seatHoldId;
        mailId = customerMailId;
        timer = durationToHold;
    }

}
