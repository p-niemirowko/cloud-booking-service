package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.port.model.PaymentSession;
import com.pniemirowko.cloud.booking.application.usecase.exception.UseCaseError;
import com.pniemirowko.cloud.booking.application.usecase.exception.UseCaseException;
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
    public void attachPayment(PaymentSession paymentSession, UUID bookingId) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new UseCaseException(UseCaseError.NOT_FOUND_BY_BOOKING_ID, bookingId));

        booking.attachPayment(paymentSession.getPaymentId(),
                paymentSession.getExpiredAt(),
                paymentSession.getRedirectUri());

        repository.save(booking);
    }
}
