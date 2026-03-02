package com.pniemirowko.cloud.booking.application.port;

import com.pniemirowko.cloud.booking.application.port.model.PropertyDetails;

public interface PropertyClient {

    PropertyDetails getPropertyDetails(String propertyId);
}
