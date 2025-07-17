package com.hrishabh.uberprojectbookingservice.service;

import com.example.uberprojectentityservice.models.Booking;
import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Driver;
import com.example.uberprojectentityservice.models.Passenger;
import com.hrishabh.uberprojectbookingservice.apis.LocationServiceApi;
import com.hrishabh.uberprojectbookingservice.apis.UberSocketApi;
import com.hrishabh.uberprojectbookingservice.config.RetrofitConfig;
import com.hrishabh.uberprojectbookingservice.dto.*;
import com.hrishabh.uberprojectbookingservice.repositories.BookingRepository;
import com.hrishabh.uberprojectbookingservice.repositories.DriverRepository;
import com.hrishabh.uberprojectbookingservice.repositories.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingSerivceImpl implements BookingService {

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final DriverRepository driverRepository;
    private final RetrofitConfig retrofitConfig;

    public BookingSerivceImpl(
            PassengerRepository passengerRepository,
            BookingRepository bookingRepository,
            RetrofitConfig retrofitConfig,
            DriverRepository driverRepository
    ) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
        this.retrofitConfig = retrofitConfig;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());

        if (passenger.isEmpty()) {
            throw new RuntimeException("Passenger not found for ID: " + bookingDetails.getPassengerId());
        }

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGINING_DRIVER)
                .pickupLocation(bookingDetails.getStartLocation())
                .dropOffLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();

        Booking newBooking = bookingRepository.save(booking);

        NearbyDriversReqestDto request = NearbyDriversReqestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        processNearbyDriversAsync(request, bookingDetails.getPassengerId(), newBooking.getId());


        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();
    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto requestDto, Long bookingId) {
        Optional<Driver> driver = driverRepository.findById(requestDto.getDriverId().get());
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (driver.isEmpty() || booking.isEmpty()) {
            throw new RuntimeException("Invalid booking or driver ID.");
        }
        //TODO : check if the driver is already assigned or not
        bookingRepository.updateBookingStatusAndDriverById(
                bookingId,
                BookingStatus.valueOf(requestDto.getStatus()),
                driver.get()
        );
        //TODO : Update the driver's availability

        return UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .bookingStatus(booking.get().getBookingStatus())
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .build();
    }

    private void processNearbyDriversAsync(NearbyDriversReqestDto request, Long passengerId, Long bookingId) {
        LocationServiceApi locationServiceApi = retrofitConfig.getLocationServiceApi();

        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(request);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocation ->
                            System.out.println("Nearby driver ID: " + driverLocation.getDriverId()));

                    raiseRideRequestAsync(RideRequestDto.builder().passengerId(passengerId).bookingId(bookingId).build());
                } else {
                    System.out.println("LocationService error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {
                System.out.println("LocationService failed:");
                throwable.printStackTrace();
            }
        });
    }

    private void raiseRideRequestAsync(RideRequestDto request) {
        try {
            UberSocketApi uberSocketApi = retrofitConfig.uberSocketApi();

            Call<Boolean> call = uberSocketApi.informNearbyDrivers(request);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("SocketService failed: " + response.code() + " - " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable throwable) {
                    System.out.println("SocketService call failed:");
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.out.println("Unexpected error calling SocketService:");
            e.printStackTrace();
        }
    }
}
