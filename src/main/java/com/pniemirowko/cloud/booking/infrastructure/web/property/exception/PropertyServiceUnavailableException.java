package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

public class PropertyServiceUnavailableException extends RuntimeException {

    public PropertyServiceUnavailableException(String message) {
        super(message);
    }

    public PropertyServiceUnavailableException(String message, Throwable ex) {
        super(message, ex);
    }
}
