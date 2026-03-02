package com.pniemirowko.cloud.booking.api.client.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PostBookingRequest {

    String propertyId;
    LocalDate from;
    LocalDate to;
}
