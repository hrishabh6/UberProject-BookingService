package com.hrishabh.uberprojectbookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverLocationDto {
    String driverId;
    double latitude;
    double longitude;
}
