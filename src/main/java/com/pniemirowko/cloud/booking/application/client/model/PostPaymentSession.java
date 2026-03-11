package com.pniemirowko.cloud.booking.application.client.model;

import java.math.BigDecimal;

public record PostPaymentSession(String bookingId,
                                 BigDecimal amount,
                                 String currency) {
}
