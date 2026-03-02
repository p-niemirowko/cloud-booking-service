package com.pniemirowko.cloud.booking.infrastructure.web.property;

import com.pniemirowko.cloud.booking.application.port.PropertyClient;
import com.pniemirowko.cloud.booking.application.port.model.PropertyDetails;
import com.pniemirowko.cloud.booking.infrastructure.web.property.exception.PropertyServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyClientWebAdapter implements PropertyClient {

    private final PropertyHttpClient client;

    @Override
    @Retry(name = "propertyService")
    @CircuitBreaker(name = "propertyService", fallbackMethod = "fallback")
    public PropertyDetails getPropertyDetails(String propertyId) {
        return client.getPropertyDetails(propertyId);
    }

    private PropertyDetails fallback(String propertyId, Throwable ex) {
        throw new PropertyServiceUnavailableException(
                "Property service unavailable", ex);
    }
}
