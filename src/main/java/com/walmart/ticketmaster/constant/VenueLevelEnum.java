package com.walmart.ticketmaster.constant;

/**
 * Created by mkambam on 12/15/15.
 */
public enum VenueLevelEnum {

    ORCHESTRA(1, 25 * 50), MAIN(2, 2 * 100), BALCONY1(3, 15 * 100), BALCONY2(4, 15 * 100);

    private final Integer level;
    private final Integer numOfSeats;


    VenueLevelEnum(Integer level,int numOfSeats) {
        this.level = level;
        this.numOfSeats = numOfSeats;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getTotalSeats() {

        return numOfSeats;
    }

    @Override
    public String toString() {
        return String.valueOf(this.level);
    }
}
