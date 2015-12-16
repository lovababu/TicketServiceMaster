package com.walmart.ticketmaster.constant;

/**
 * Created by mkambam on 12/15/2015.
 */

public enum  SeatStatus {

    AVAILABLE("available"), HOLD("hold"), RESERVED("reserved");

    private final String status;

    SeatStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}