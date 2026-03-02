package com.pniemirowko.cloud.booking.application.port;

import com.pniemirowko.cloud.booking.application.port.model.PaymentSession;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentClient {

    PaymentSession createSession(UUID bookingId, BigDecimal amount, String currency);
}
