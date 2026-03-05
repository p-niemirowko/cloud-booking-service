package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

import com.pniemirowko.cloud.booking.infrastructure.web.exception.ServerHttpException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PropertyServerHttpException extends ServerHttpException {

    public PropertyServerHttpException(HttpStatus status, String message) {
        super(status, message);
    }
}
