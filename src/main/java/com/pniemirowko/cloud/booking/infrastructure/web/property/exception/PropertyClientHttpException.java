package com.pniemirowko.cloud.booking.infrastructure.web.property.exception;

import com.pniemirowko.cloud.booking.infrastructure.web.exception.ClientHttpException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PropertyClientHttpException extends ClientHttpException {

    public PropertyClientHttpException(HttpStatus status, String message) {
        super(status, message);
    }
}
