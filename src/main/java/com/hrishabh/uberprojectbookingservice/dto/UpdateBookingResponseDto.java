package com.hrishabh.uberprojectbookingservice.dto;

import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookingResponseDto {

    private Long bookingId;

    private BookingStatus bookingStatus;

    private Optional<Driver> driver;

}
