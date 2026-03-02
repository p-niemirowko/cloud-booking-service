package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingCommand;
import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingResponse;
import com.pniemirowko.cloud.booking.application.port.PaymentClient;
import com.pniemirowko.cloud.booking.application.port.model.PaymentSession;
import com.pniemirowko.cloud.booking.domain.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final InitializeBookingUseCase initializeBookingUseCase;
    private final PaymentClient paymentClient;
    private final AttachPaymentUseCase attachPaymentUseCase;
    private final CreateBookingMapper mapper;

    public CreateBookingResponse execute(CreateBookingCommand command) {
        log.debug("CreateBookingUseCase.execute for command: {}", command);

        Booking booking = initializeBookingUseCase.execute(command);

        PaymentSession paymentSession = paymentClient
                .createSession(booking.getId(),
                        booking.getTotalPrice().getAmount(),
                        booking.getTotalPrice().getCurrency());

        attachPaymentUseCase.attachPayment(booking.getId(), paymentSession.getPaymentId());

        return mapper.toResponse(booking, paymentSession.getRedirectUri(), paymentSession.getExpiredAt());
    }
}
