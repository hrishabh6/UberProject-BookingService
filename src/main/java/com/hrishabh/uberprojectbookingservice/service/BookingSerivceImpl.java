package com.hrishabh.uberprojectbookingservice.service;


import com.example.uberprojectentityservice.models.Booking;
import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Passenger;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingDto;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingResponseDto;
import com.hrishabh.uberprojectbookingservice.dto.DriverLocationDto;
import com.hrishabh.uberprojectbookingservice.dto.NearbyDriversReqestDto;
import com.hrishabh.uberprojectbookingservice.repositories.BookingRepository;
import com.hrishabh.uberprojectbookingservice.repositories.PassengerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingSerivceImpl implements BookingService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private static final String LOCATION_SERVICE_URL = "http://localhost:7477";
    private static final double bookingDeta = 53.339428;

    public BookingSerivceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
    }





    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGINING_DRIVER)
                .pickupLocation(bookingDetails.getStartLocation())
                .dropOffLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();

        NearbyDriversReqestDto request = NearbyDriversReqestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE_URL+"/api/location/nearby/drivers",request, DriverLocationDto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocation -> {
                System.out.println(driverLocation.getDriverId() + " Latitude : " + driverLocation.getLatitude() + " Longitude : " + driverLocation.getLongitude());
            });
        } else {
            System.out.println("Error in getting driver location");
        }


        Booking newBooking = bookingRepository.save(booking);
        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();
    }
}
