package com.pniemirowko.cloud.booking.infrastructure.web.payment;

import com.pniemirowko.cloud.booking.application.client.model.PaymentSessionResponse;
import com.pniemirowko.cloud.booking.application.client.model.PostPaymentSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/service")
public interface PaymentHttpClient {

    @PostExchange
    PaymentSessionResponse createPaymentSession(@RequestBody PostPaymentSession request);
}
