package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.domain.Booking;
import com.pniemirowko.cloud.booking.domain.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class AttachPaymentUseCase {

    private final BookingRepository repository;

    @Transactional
    public void attachPayment(UUID bookingId, String paymentId) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Data not found"));// todo add exception handler

        booking.attachPayment(paymentId);

        repository.save(booking);
    }
}
