package com.hrishabh.uberprojectbookingservice.apis;

import com.hrishabh.uberprojectbookingservice.dto.DriverLocationDto;
import com.hrishabh.uberprojectbookingservice.dto.NearbyDriversReqestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {

    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearbyDrivers(@Body NearbyDriversReqestDto request);

}
