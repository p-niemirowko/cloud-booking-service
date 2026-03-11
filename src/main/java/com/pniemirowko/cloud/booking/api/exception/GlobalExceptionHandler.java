package com.pniemirowko.cloud.booking.api.exception;

import com.pniemirowko.cloud.booking.api.client.dto.ErrorHttpResponse;
import com.pniemirowko.cloud.booking.application.exception.UseCaseError;
import com.pniemirowko.cloud.booking.application.exception.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pniemirowko.cloud.booking.infrastructure.web.exception.RemoteServiceException;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = UseCaseException.class)
    public ResponseEntity<ErrorHttpResponse> handleUseCaseException(UseCaseException exception,
                                                                    ServerHttpRequest request) {
        log.error("Exception has been thrown in {} with message: {}",
                exception.getUseCaseName(),
                exception.getMessage(),
                exception);

        HttpStatus status = mapUseCaseStatus(exception.getError());

        return ResponseEntity
                .status(status)
                .body(ErrorHttpResponse.builder()
                        .path(request.getURI().getPath())
                        .message(exception.getMessage())
                        .status(status)
                        .build());
    }

    @ExceptionHandler(exception = RemoteServiceException.class)
    public ResponseEntity<ErrorHttpResponse> handleRemoteServiceException(RemoteServiceException exception,
                                                                          ServerHttpRequest request) {
        log.error("Remote service {} failed with status {}",
                exception.getService(),
                exception.getStatus(),
                exception);

        HttpStatus status = mapRemoteServiceStatus(exception.getStatus());

        return ResponseEntity
                .status(status)
                .body(ErrorHttpResponse.builder()
                        .path(request.getURI().getPath())
                        .message(exception.getMessage())
                        .status(status)
                        .build());
    }

    private HttpStatus mapUseCaseStatus(UseCaseError error) {
        return switch (error) {
            case NOT_FOUND_BY_IDEMPOTENCY_KEY, NOT_FOUND_BY_BOOKING_ID -> NOT_FOUND;
            case INVALID_PERIOD -> HttpStatus.BAD_REQUEST;
            case PROPERTY_NOT_AVAILABLE -> HttpStatus.CONFLICT;
        };
    }

    private HttpStatus mapRemoteServiceStatus(HttpStatus status) {
        if (status == NOT_FOUND) {
            return NOT_FOUND;
        }
        if (status == SERVICE_UNAVAILABLE) {
            return SERVICE_UNAVAILABLE;
        }
        if (status.is4xxClientError() || status.is5xxServerError()) {
            return BAD_GATEWAY;
        }
        return INTERNAL_SERVER_ERROR;
    }
}
