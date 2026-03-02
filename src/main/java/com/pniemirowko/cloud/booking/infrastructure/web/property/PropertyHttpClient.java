package com.pniemirowko.cloud.booking.infrastructure.web.property;

import com.pniemirowko.cloud.booking.application.port.model.PropertyDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/service")
interface PropertyHttpClient {

    @GetExchange("/{propertyId}")
    PropertyDetails getPropertyDetails(@PathVariable String propertyId);
}
