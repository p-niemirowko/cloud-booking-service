package com.pniemirowko.cloud.booking.domain;

import com.pniemirowko.cloud.booking.application.exception.UseCaseError;
import com.pniemirowko.cloud.booking.application.exception.UseCaseException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record StayPeriod(LocalDate from, LocalDate to) {

    public long nights() {
        if (!to.isAfter(from)) {
            throw new UseCaseException(UseCaseError.INVALID_PERIOD);
        }
        return ChronoUnit.DAYS.between(from, to);
    }
}
