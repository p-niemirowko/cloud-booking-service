package com.pniemirowko.cloud.booking.infrastructure.persistence;


import com.pniemirowko.cloud.booking.domain.BookingStatus;
import com.pniemirowko.cloud.booking.domain.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class BookingEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private UUID ownerId;
    private UUID propertyId;

    private UUID paymentId;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    private BookingStatus status;

    private String paymentRedirectUri;
    private LocalDateTime paymentExpiresAt;

    private BigDecimal totalPrice;
    private String currency;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    private String idempotencyKey;
}
