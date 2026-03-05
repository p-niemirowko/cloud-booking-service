package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.port.PropertyClient;
import com.pniemirowko.cloud.booking.application.port.model.PropertyDetails;
import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingCommand;
import com.pniemirowko.cloud.booking.application.usecase.exception.UseCaseError;
import com.pniemirowko.cloud.booking.application.usecase.exception.UseCaseException;
import com.pniemirowko.cloud.booking.domain.Booking;
import com.pniemirowko.cloud.booking.domain.BookingRepository;
import com.pniemirowko.cloud.booking.domain.StayPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class InitializeBookingUseCase {

    private final BookingRepository bookingRepository;
    private final PropertyClient propertyClient;

    @Transactional
    Booking execute(CreateBookingCommand command) {
        checkAvailability(command);

        BigDecimal totalPrice = calculateTotalPrice(command.getPropertyId(), command.getFrom(), command.getTo());

        return saveNewBooking(command, totalPrice);
    }

    private Booking saveNewBooking(CreateBookingCommand command, BigDecimal totalPrice) {
        try {
            Booking booking = Booking.initializeBooking(
                    command.getPropertyId(),
                    command.getFrom(),
                    command.getTo(),
                    totalPrice,
                    command.getOwnerId(),
                    command.getIdempotencyKey());

            bookingRepository.save(booking);

            return booking;
        } catch (DataIntegrityViolationException ex) {
            return bookingRepository.findByIdempotencyKey(command.getIdempotencyKey())
                    .orElseThrow(() -> new UseCaseException(UseCaseError.NOT_FOUND_BY_IDEMPOTENCY_KEY, ex));
        }
    }

    private void checkAvailability(CreateBookingCommand command) {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    private BigDecimal calculateTotalPrice(String propertyId, LocalDate from, LocalDate to) {
        PropertyDetails propertyDetails = propertyClient.getPropertyDetails(propertyId);

        long nights = new StayPeriod(from, to).nights();

        return propertyDetails.getPrice().multiply(BigDecimal.valueOf(nights));
    }
}
