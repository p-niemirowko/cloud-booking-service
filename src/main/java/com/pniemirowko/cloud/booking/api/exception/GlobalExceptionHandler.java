package com.pniemirowko.cloud.booking.api.exception;

import com.pniemirowko.cloud.booking.application.usecase.exception.UseCaseException;
import com.pniemirowko.cloud.booking.infrastructure.web.property.exception.PropertyNotFoundException;
import com.pniemirowko.cloud.booking.infrastructure.web.property.exception.PropertyServerHttpException;
import com.pniemirowko.cloud.booking.infrastructure.web.property.exception.PropertyServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = PropertyNotFoundException.class)
    public ResponseEntity<ErrorHttpResponse> handlePropertyNotFoundHandler(PropertyNotFoundException exception,
                                                                           ServerHttpRequest request) {
        log.error("Cannot find property in the system", exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorHttpResponse.builder()
                        .path(request.getURI().getPath())
                        .message("Property not found")
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(exception = PropertyServerHttpException.class)
    public ResponseEntity<ErrorHttpResponse> handlePropertyServiceExceptionHandler(PropertyServerHttpException exception,
                                                                                   ServerHttpRequest request) {
        log.error("property-service return {} error", exception.getStatus(), exception);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorHttpResponse.builder()
                        .path(request.getURI().getPath())
                        .message("Property service failed with status")
                        .status(INTERNAL_SERVER_ERROR)
                        .build());
    }

    @ExceptionHandler(exception = PropertyServiceUnavailableException.class)
    public ResponseEntity<ErrorHttpResponse> handlePropertyServiceUnavailable(PropertyServiceUnavailableException exception,
                                                                              ServerHttpRequest request) {
        log.error("property-service is not available", exception);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorHttpResponse.builder()
                        .path(request.getURI().getPath())
                        .message("Property service is unavailable")
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .build());
    }

    @ExceptionHandler(exception = UseCaseException.class)
    public ResponseEntity<ErrorHttpResponse> handleUseCaseException(UseCaseException exception,
                                                                    ServerHttpRequest request) {
        log.error("Exception has been thrown in {} with message: {}",
                exception.getUseCaseName(),
                exception.getMessage(),
                exception);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorHttpResponse.builder()
                        .path(request.getURI().getPath())
                        .message("Unexpected error")
                        .status(INTERNAL_SERVER_ERROR)
                        .build());
    }
}
