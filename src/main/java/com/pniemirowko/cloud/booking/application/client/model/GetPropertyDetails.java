package com.pniemirowko.cloud.booking.application.client.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GetPropertyDetails {

    String propertyId;
    BigDecimal price;
}
