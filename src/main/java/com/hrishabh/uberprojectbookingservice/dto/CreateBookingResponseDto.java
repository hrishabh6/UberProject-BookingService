package com.hrishabh.uberprojectbookingservice.dto;

import com.example.uberprojectentityservice.models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponseDto {
    private Long bookingId;
    private String bookingStatus;
    private Optional<Driver> driver;
}
