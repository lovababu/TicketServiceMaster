package com.walmart.ticketmaster.exception;

/**
 * Created by mkambam on 12/15/15.
 */
public class MissingCustomerEmailException extends RuntimeException {
    public MissingCustomerEmailException(String message) {
        super(message);
    }
}
