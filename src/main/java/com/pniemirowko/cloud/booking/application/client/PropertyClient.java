package com.pniemirowko.cloud.booking.application.client;

import com.pniemirowko.cloud.booking.application.client.model.GetPropertyDetails;

public interface PropertyClient {

    GetPropertyDetails getPropertyDetails(String propertyId);
}
