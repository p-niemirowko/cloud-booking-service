package com.pniemirowko.cloud.booking.application.usecase.create.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CreateBookingCommand {

    UUID propertyId;
    UUID ownerId;
    LocalDate from;
    LocalDate to;
    String idempotencyKey;
}
