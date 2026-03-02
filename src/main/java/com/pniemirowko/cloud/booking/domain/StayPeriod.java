package com.pniemirowko.cloud.booking.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record StayPeriod(LocalDate from, LocalDate to) {

    public long nights() {
        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("Invalid period");
        }
        return ChronoUnit.DAYS.between(from, to);
    }
}
