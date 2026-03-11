package com.pniemirowko.cloud.booking.infrastructure.web.property;

import com.pniemirowko.cloud.booking.application.client.model.GetPropertyDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/service")
interface PropertyHttpClient {

    @GetExchange("/{propertyId}")
    GetPropertyDetails getPropertyDetails(@PathVariable String propertyId);
}
