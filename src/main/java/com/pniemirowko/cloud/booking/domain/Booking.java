package com.pniemirowko.cloud.booking.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Booking {

    private UUID id;

    private UUID ownerId;
    private UUID propertyId;
    private UUID paymentId;
    private LocalDate from;
    private LocalDate to;
    private BookingStatus status;
    private String paymentRedirectUri;
    private String paymentExpiredAt;
    private BigDecimal totalPrice;
    private String currency;
    private LocalDateTime expiredAt;

    public static Booking initializeBooking(UUID propertyId,
                                            LocalDate from,
                                            LocalDate to,
                                            BigDecimal totalPrice,
                                            UUID ownerId) {

        return Booking.builder()
                .id(UUID.randomUUID())
                .propertyId(propertyId)
                .from(from)
                .to(to)
                .totalPrice(totalPrice)
                .currency("PLN")
                .status(BookingStatus.INITIALIZED)
                .ownerId(ownerId)
                .expiredAt(LocalDateTime.now().plusMinutes(20)) // todo change it to configuration
                .build();
    }

    public void attachPayment(String paymentId, String expiredAt, String redirectUri) {
        if (status != BookingStatus.INITIALIZED) {
            throw new IllegalArgumentException("Invalid status state");
        }
        this.paymentId = UUID.fromString(paymentId);
        this.paymentExpiredAt = expiredAt;
        this.paymentRedirectUri = redirectUri;
        this.status = BookingStatus.PENDING;
    }
}
