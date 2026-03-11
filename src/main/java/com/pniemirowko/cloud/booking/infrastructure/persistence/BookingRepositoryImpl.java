package com.pniemirowko.cloud.booking.infrastructure.persistence;

import com.pniemirowko.cloud.booking.domain.Booking;
import com.pniemirowko.cloud.booking.domain.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class BookingRepositoryImpl implements BookingRepository {

    private final JpaBookingRepository jpaRepository;
    private final BookingPersistenceMapper mapper;

    @Override
    public Optional<Booking> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Booking booking) {
        jpaRepository.save(mapper.toEntity(booking));
    }

    @Override
    public Optional<Booking> findByIdempotencyKey(String idempotencyKey) {
        return jpaRepository.findByIdempotencyKey(idempotencyKey)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsOverlappingBooking(UUID propertyId, LocalDate from, LocalDate to) {
        return jpaRepository.existsOverlappingBooking(propertyId, from, to);
    }
}
