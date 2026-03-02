package com.pniemirowko.cloud.booking.domain;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

    Optional<Booking> findById(UUID id);

    void save(Booking booking);

    Optional<Booking> findByIdempotencyKey(String idempotencyKey);
}
