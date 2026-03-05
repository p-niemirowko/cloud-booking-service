package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

import com.pniemirowko.cloud.booking.infrastructure.web.exception.ServerHttpException;
import org.springframework.http.HttpStatus;

public class PropertyServiceUnavailableException extends ServerHttpException {

    public PropertyServiceUnavailableException(String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message, cause);
    }
}
