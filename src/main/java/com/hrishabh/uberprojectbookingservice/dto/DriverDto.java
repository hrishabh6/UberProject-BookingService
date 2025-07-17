package com.hrishabh.uberprojectbookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDto {
    private Long id;
    private String name;
    private String licenceNumber;
    private double rating;
}
