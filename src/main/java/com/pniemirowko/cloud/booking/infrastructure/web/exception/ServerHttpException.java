package com.pniemirowko.cloud.booking.infrastructure.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServerHttpException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public ServerHttpException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public ServerHttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
