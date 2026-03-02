package com.pniemirowko.cloud.booking.application.port.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentSession {

    String paymentId;
    String redirectUri;
    String expiredAt;
}
