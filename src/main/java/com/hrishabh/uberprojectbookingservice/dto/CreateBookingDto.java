package com.hrishabh.uberprojectbookingservice.dto;


import com.example.uberprojectentityservice.models.ExactLocation;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDto {
    private Long passengerId;
    private ExactLocation startLocation;
    private ExactLocation endLocation;
}
