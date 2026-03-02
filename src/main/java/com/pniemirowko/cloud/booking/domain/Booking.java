package com.pniemirowko.cloud.booking.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Booking {

    private UUID id;

    private String ownerId;
    private String propertyId;
    private String paymentId;
    private LocalDate from;
    private LocalDate to;
    private BookingStatus status;
    private Money totalPrice;
    private String idempotencyKey;

    public static Booking initializeBooking(String propertyId,
                                            LocalDate from,
                                            LocalDate to,
                                            BigDecimal totalPrice,
                                            String ownerId,
                                            String idempotencyKey) {

        return Booking.builder()
                .id(UUID.randomUUID())
                .propertyId(propertyId)
                .from(from)
                .to(to)
                .totalPrice(Money.builder()
                        .amount(totalPrice)
                        .currency("PLN")
                        .build())
                .status(BookingStatus.INITIALIZED)
                .ownerId(ownerId)
                .idempotencyKey(idempotencyKey)
                .build();
    }

    public void attachPayment(String paymentId) {
        if (status != BookingStatus.INITIALIZED) {
            throw new IllegalArgumentException("Invalid status state");
        }
        this.paymentId = paymentId;
        this.status = BookingStatus.PENDING;
    }
}
