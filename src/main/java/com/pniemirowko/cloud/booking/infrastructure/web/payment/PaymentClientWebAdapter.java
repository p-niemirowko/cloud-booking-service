package com.pniemirowko.cloud.booking.infrastructure.web.payment;

import com.pniemirowko.cloud.booking.application.client.PaymentClient;
import com.pniemirowko.cloud.booking.application.client.model.PaymentSessionResponse;
import com.pniemirowko.cloud.booking.application.client.model.PostPaymentSession;
import com.pniemirowko.cloud.booking.infrastructure.web.RemoteService;
import com.pniemirowko.cloud.booking.infrastructure.web.exception.RemoteServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class PaymentClientWebAdapter implements PaymentClient {

    private final PaymentHttpClient client;

    @Override
    @Retry(name = "paymentService")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallback")
    public PaymentSessionResponse createSession(UUID bookingId, BigDecimal amount, String currency) {
        return client.createPaymentSession(new PostPaymentSession(bookingId.toString(), amount, currency));
    }

    private PaymentSessionResponse fallback(UUID bookingId, BigDecimal amount, String currency, Throwable ex) {
        throw new RemoteServiceException(
                RemoteService.PAYMENT,
                org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,
                "Payment service unavailable",
                ex);
    }
}
