package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by mkambam on 12/14/15.
 */
@Setter
@Getter
public class VenueLevel {

    private Integer levelId;

    private String name;


    private Double price;

    private List<Seat> seats;
}
