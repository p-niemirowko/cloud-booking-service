package com.pniemirowko.cloud.booking.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface JpaBookingRepository extends JpaRepository<BookingEntity, UUID> {

    Optional<BookingEntity> findByIdempotencyKey(String idempotencyKey);
    boolean existsByPropertyIdAndFromBeforeAnd
}
