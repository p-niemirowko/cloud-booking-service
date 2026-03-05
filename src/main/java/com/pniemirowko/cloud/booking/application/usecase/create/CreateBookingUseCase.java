package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingCommand;
import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingResponse;
import com.pniemirowko.cloud.booking.application.port.PaymentClient;
import com.pniemirowko.cloud.booking.application.port.model.PaymentSession;
import com.pniemirowko.cloud.booking.domain.Booking;
import com.pniemirowko.cloud.booking.domain.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return bookingRepository.findByIdempotencyKey(command.getIdempotencyKey())
                .map(mapper::toResponse)
                .orElseGet(() -> createNewBooking(command));
    }

    private CreateBookingResponse createNewBooking(CreateBookingCommand command) {

        Booking booking = initializeBookingUseCase.execute(command);

        PaymentSession paymentSession = paymentClient.createSession(
                booking.getId(),
                booking.getTotalPrice().getAmount(),
                booking.getTotalPrice().getCurrency());

        attachPaymentUseCase.attachPayment(paymentSession, booking.getId());
        return mapper.toResponse(booking);
    }
}
