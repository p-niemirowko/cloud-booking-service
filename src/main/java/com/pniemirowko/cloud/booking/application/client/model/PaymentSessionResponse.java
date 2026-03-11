package com.pniemirowko.cloud.booking.application.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentSessionResponse {

    String paymentId;
    String redirectUri;
    String expiredAt;
}
