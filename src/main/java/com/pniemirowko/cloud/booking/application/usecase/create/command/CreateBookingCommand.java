package com.pniemirowko.cloud.booking.application.usecase.create.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateBookingCommand {

    String propertyId;
    String ownerId;
    LocalDate from;
    LocalDate to;
    String idempotencyKey;
}
