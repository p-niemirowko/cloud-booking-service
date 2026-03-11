package com.pniemirowko.cloud.booking.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

interface JpaBookingRepository extends JpaRepository<BookingEntity, UUID> {

    Optional<BookingEntity> findByIdempotencyKey(String idempotencyKey);

    @Query("""
                SELECT COUNT(b) > 0
                FROM BookingEntity b
                WHERE b.propertyId = :propertyId
                AND b.status IN ('PENDING','CONFIRMED')
                AND b.dateFrom < :dateTo
                AND b.dateTo > :dateFrom
            """)
    boolean existsOverlappingBooking(
            UUID propertyId,
            LocalDate dateFrom,
            LocalDate dateTo
    );
}
