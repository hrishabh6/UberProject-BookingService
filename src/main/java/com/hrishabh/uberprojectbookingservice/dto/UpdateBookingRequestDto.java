package com.hrishabh.uberprojectbookingservice.dto;

import com.example.uberprojectentityservice.models.BookingStatus;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookingRequestDto {

    private String status;
    private Optional<Long> driverId;

}
