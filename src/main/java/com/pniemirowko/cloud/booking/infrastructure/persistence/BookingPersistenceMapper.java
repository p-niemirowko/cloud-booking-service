package com.pniemirowko.cloud.booking.infrastructure.persistence;

import com.pniemirowko.cloud.booking.domain.Booking;
import com.pniemirowko.cloud.booking.domain.BookingRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface BookingPersistenceMapper {

    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking booking);
}
