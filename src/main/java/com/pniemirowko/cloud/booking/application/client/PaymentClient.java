package com.pniemirowko.cloud.booking.application.client;

import com.pniemirowko.cloud.booking.application.client.model.PaymentSessionResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentClient {

    PaymentSessionResponse createSession(UUID bookingId, BigDecimal amount, String currency);
}
