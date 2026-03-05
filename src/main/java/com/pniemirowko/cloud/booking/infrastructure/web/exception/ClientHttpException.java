package com.pniemirowko.cloud.booking.infrastructure.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ClientHttpException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public ClientHttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
