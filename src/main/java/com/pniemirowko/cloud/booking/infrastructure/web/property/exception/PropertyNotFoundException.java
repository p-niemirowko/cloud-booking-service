package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PropertyNotFoundException extends PropertyServiceHttpException {

    public PropertyNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
