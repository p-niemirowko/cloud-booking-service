package com.pniemirowko.cloud.booking.api.client;

import com.pniemirowko.cloud.booking.api.client.dto.PostBookingRequest;
import com.pniemirowko.cloud.booking.application.usecase.create.CreateBookingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
class BookingController {

    private final CreateBookingUseCase create;
    private final BookingHttpMapper bookingHttpMapper;

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingDetails(@PathVariable String bookingId, String ownerId) {
        log.info("Getting details about booking with id {}", bookingId);

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyBookings(Jwt jwt) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody PostBookingRequest request,
                                           @RequestHeader("Idempotency-key") String idempotencyKey,
                                           @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        log.info("Create new booking for user {} and property {}", userId, request.getPropertyId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(create.execute(bookingHttpMapper.toCommand(request, userId, idempotencyKey)));
    }

    @PostMapping("/{bookingId}/cancel")
    public void cancelBooking(@PathVariable String bookingId, Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        log.info("Delete booking with id {} for user {}", bookingId, userId);

        throw new UnsupportedOperationException("Not implemented yet");
    }

}

