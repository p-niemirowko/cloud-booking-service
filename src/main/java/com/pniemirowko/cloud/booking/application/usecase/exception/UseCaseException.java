package com.pniemirowko.cloud.booking.application.usecase.exception;

import lombok.Getter;

public class UseCaseException extends RuntimeException {

    @Getter
    private final String useCaseName;

    public UseCaseException(UseCaseError useCaseError, Object... args) {
        super(useCaseError.formatMessage(args));
        this.useCaseName = useCaseError.getUseCaseName();
    }

    public UseCaseException(UseCaseError useCaseError, Throwable cause, Object... args) {
        super(useCaseError.formatMessage(args), cause);
        this.useCaseName = useCaseError.getUseCaseName();
    }
}
