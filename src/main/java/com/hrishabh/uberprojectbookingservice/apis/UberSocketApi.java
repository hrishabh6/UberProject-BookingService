package com.hrishabh.uberprojectbookingservice.apis;

import com.hrishabh.uberprojectbookingservice.dto.DriverLocationDto;
import com.hrishabh.uberprojectbookingservice.dto.NearbyDriversReqestDto;
import com.hrishabh.uberprojectbookingservice.dto.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UberSocketApi {
    @POST("/api/socket/newride")
    Call<Boolean> informNearbyDrivers(@Body RideRequestDto request);

}
