package com.walmart.ticketmaster.domain;

import com.walmart.ticketmaster.constant.SeatStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mkambam on 12/14/15.
 */
@Setter
@Getter
public class Seat {
    private Integer id;

    private SeatStatus status;

    private Integer venueLevelId;

    private Integer seatHoldId;

    private Long timer = System.currentTimeMillis();

    public boolean isAvailable() {
        //it should be neither hold nor reserved.
        return status != SeatStatus.RESERVED && status != SeatStatus.HOLD && timer < System.currentTimeMillis();
    }





}
