package com.hrishabh.uberprojectbookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearbyDriversReqestDto {
    double latitude;
    double longitude;
}
