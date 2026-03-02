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

        return ResponseEntity.of(bookingFacade.getBookingDetails(bookingId, ownerId));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyBookings(Jwt jwt) {
        return ResponseEntity.ok(bookingFacade.getMyBookings(jwt.getClaimAsString("userId")));
    }

    // todo add exception handler for http properties and payments
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody PostBookingRequest request,
                                           @RequestHeader("Idempotency-key") String idempotencyKey,
                                           @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        log.info("Create new booking for user {} and property {}", userId, request.getPropertyId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(create.execute(bookingHttpMapper.toCommand(request, userId)));
    }

    @PostMapping("/{bookingId}/cancel")
    public void cancelBooking(@PathVariable String bookingId, Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        log.info("Delete booking with id {} for user {}", bookingId, userId);

        bookingFacade.cancelBooking(bookingId, userId);
    }

}

