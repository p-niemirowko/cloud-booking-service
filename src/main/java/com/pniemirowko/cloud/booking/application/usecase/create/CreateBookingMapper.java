package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingResponse;
import com.pniemirowko.cloud.booking.domain.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CreateBookingMapper {

    @Mapping(target = "redirectUri", source = "redirectUri")
    @Mapping(target = "expiredAt", source = "expiredAt")
    CreateBookingResponse toResponse(Booking booking, String redirectUri, String expiredAt);
}
