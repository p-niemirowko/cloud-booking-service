package com.pniemirowko.cloud.booking.application.exception;

import lombok.Getter;

public class UseCaseException extends RuntimeException {

    @Getter
    private final String useCaseName;
    @Getter
    private final UseCaseError error;

    public UseCaseException(UseCaseError error, Object... args) {
        super(error.formatMessage(args));
        this.error = error;
        this.useCaseName = error.getUseCaseName();
    }

    public UseCaseException(UseCaseError error, Throwable cause, Object... args) {
        super(error.formatMessage(args), cause);
        this.error = error;
        this.useCaseName = error.getUseCaseName();
    }
}
