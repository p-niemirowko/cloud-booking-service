package com.pniemirowko.cloud.booking.api.client;

import com.pniemirowko.cloud.booking.api.client.dto.PostBookingRequest;
import com.pniemirowko.cloud.booking.application.usecase.create.command.CreateBookingCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface BookingHttpMapper {

    @Mapping(target = "ownerId", source = "ownerId")
    CreateBookingCommand toCommand(PostBookingRequest request, String ownerId);

}
