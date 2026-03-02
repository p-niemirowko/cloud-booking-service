package com.pniemirowko.cloud.booking.infrastructure.web.payment;

import com.pniemirowko.cloud.booking.application.port.PaymentClient;
import com.pniemirowko.cloud.booking.application.port.model.PaymentSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
class PaymentClientWebAdapter implements PaymentClient {

    // todo add timeout, circuit breaker, retry
    @Override
    public PaymentSession createSession(UUID bookingId, BigDecimal amount, String currency) {
        // todo implement method
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
