package com.pniemirowko.cloud.booking.infrastructure.persistence;


import com.pniemirowko.cloud.booking.domain.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class BookingEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private String ownerId;
    private String propertyId;
    private LocalDate from;
    private LocalDate to;
    private BookingStatus bookingStatus;
    private BigDecimal totalPrice;
    private String currency;
    private String idempotencyKey;
}
