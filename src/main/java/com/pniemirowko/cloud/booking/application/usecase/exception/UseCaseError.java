package com.pniemirowko.cloud.booking.application.usecase.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UseCaseError {

    NOT_FOUND_BY_IDEMPOTENCY_KEY("Cannot find booking by idempotencyKey", "Create.initialize"),
    NOT_FOUND_BY_BOOKING_ID("Cannot find booking with id %s", "Create.attachPayment")
    ;

    private final String messageTemplate;

    @Getter
    private final String useCaseName;

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }

}
