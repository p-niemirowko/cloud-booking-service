package com.pniemirowko.cloud.booking.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UseCaseError {

    NOT_FOUND_BY_IDEMPOTENCY_KEY("Cannot find booking by idempotencyKey", "Create.initialize"),
    NOT_FOUND_BY_BOOKING_ID("Cannot find booking with id %s", "Create.attachPayment"),
    INVALID_PERIOD("Invalid period", "Create.initialize"),
    PROPERTY_NOT_AVAILABLE("Property is not available for the selected dates", "Create.attachPayment")
    ;

    private final String messageTemplate;

    @Getter
    private final String useCaseName;

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}
