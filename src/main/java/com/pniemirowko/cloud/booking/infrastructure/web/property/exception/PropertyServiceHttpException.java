package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PropertyServiceHttpException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public PropertyServiceHttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
