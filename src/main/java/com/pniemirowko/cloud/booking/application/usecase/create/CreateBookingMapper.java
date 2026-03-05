package com.pniemirowko.cloud.booking.application.usecase.create;

import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingResponse;
import com.pniemirowko.cloud.booking.domain.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CreateBookingMapper {

    CreateBookingResponse toResponse(Booking booking);
}
