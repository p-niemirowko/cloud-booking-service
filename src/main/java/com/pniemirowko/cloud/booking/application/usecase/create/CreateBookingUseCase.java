package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingCommand;
import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingResponse;
import com.pniemirowko.cloud.booking.application.client.PaymentClient;
import com.pniemirowko.cloud.booking.application.client.model.PaymentSessionResponse;
import com.pniemirowko.cloud.booking.domain.Booking;
import com.pniemirowko.cloud.booking.domain.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final InitializeBookingUseCase initializeBookingUseCase;
    private final AttachPaymentUseCase attachPaymentUseCase;
    private final BookingRepository bookingRepository;
    private final CreateBookingMapper mapper;

    private final PaymentClient paymentClient;

    public CreateBookingResponse execute(CreateBookingCommand command) {
        log.debug("CreateBookingUseCase.execute for command: {}", command);

        Optional<Booking> existingBooking = bookingRepository.findByIdempotencyKey(command.getIdempotencyKey());

        if (existingBooking.isEmpty()) {
            return createNewBooking(command);
        }
        Booking booking = existingBooking.get();

        // if payment session is null try to create new session (if session exist in payment-service will be returned)
        if (isNull(booking.getPaymentId())) {
            createSession(booking);
        }

        return mapper.toResponse(booking);
    }

    private CreateBookingResponse createNewBooking(CreateBookingCommand command) {

        Booking booking = initializeBookingUseCase.execute(command);

        createSession(booking);
        return mapper.toResponse(booking);
    }

    private void createSession(Booking booking) {
        // todo jeśli się nie powiedzie zwrócić null w sesji czy 500
        PaymentSessionResponse paymentSession = paymentClient.createSession(
                booking.getId(),
                booking.getTotalPrice(),
                booking.getCurrency());

        attachPaymentUseCase.attachPayment(paymentSession, booking.getId());
    }
}
