package com.pniemirowko.cloud.booking.infrastructure.web.property;

import com.pniemirowko.cloud.booking.application.client.PropertyClient;
import com.pniemirowko.cloud.booking.application.client.model.GetPropertyDetails;
import com.pniemirowko.cloud.booking.infrastructure.web.RemoteService;
import com.pniemirowko.cloud.booking.infrastructure.web.exception.RemoteServiceException;
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
    public GetPropertyDetails getPropertyDetails(String propertyId) {
        return client.getPropertyDetails(propertyId);
    }

    private GetPropertyDetails fallback(String propertyId, Throwable ex) {
        throw new RemoteServiceException(
                RemoteService.PROPERTY,
                org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,
                "Property service unavailable",
                ex);
    }
}
