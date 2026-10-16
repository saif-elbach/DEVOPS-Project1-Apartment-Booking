package com.unibz.booking.exception;
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
