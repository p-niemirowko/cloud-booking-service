package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PropertyNotFoundException extends PropertyClientHttpException {

    public PropertyNotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
