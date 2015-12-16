package com.walmart.ticketmaster.exception;

/**
 * Created by mkambam on 12/15/15.
 */
public class MissingSeatsToHoldException extends RuntimeException {
    public MissingSeatsToHoldException(String message) {
        super(message);
    }
}
