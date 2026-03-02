package com.pniemirowko.cloud.booking.application.port.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PropertyDetails {

    String propertyId;
    BigDecimal price;
}
