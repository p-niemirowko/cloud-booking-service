package com.pniemirowko.cloud.booking.infrastructure.web.exception;

import com.pniemirowko.cloud.booking.infrastructure.web.RemoteService;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RemoteServiceException extends RuntimeException {

    @Getter
    private final RemoteService service;
    @Getter
    private final HttpStatus status;

    public RemoteServiceException(RemoteService service, HttpStatus status, String message) {
        super(message);
        this.service = service;
        this.status = status;
    }

    public RemoteServiceException(RemoteService service, HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.service = service;
        this.status = status;
    }
}
