package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.port.PropertyClient;
import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingCommand;
import com.pniemirowko.cloud.booking.application.port.model.PropertyDetails;
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
        BigDecimal totalPrice = calculateTotalPrice(command.getPropertyId(), command.getFrom(), command.getTo());

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
                     .orElseThrow(() -> new RuntimeException("Unknown exception"));
        }
    }

    private BigDecimal calculateTotalPrice(String propertyId, LocalDate from, LocalDate to) {
        PropertyDetails propertyDetails = propertyClient.getPropertyDetails(propertyId);

        long nights = new StayPeriod(from, to).nights();

        return propertyDetails.getPrice().multiply(BigDecimal.valueOf(nights));
    }
}
