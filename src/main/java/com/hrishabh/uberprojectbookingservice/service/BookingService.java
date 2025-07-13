package com.hrishabh.uberprojectbookingservice.service;

import com.example.uberprojectentityservice.models.Booking;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingDto;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingResponseDto;

public interface BookingService {

    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);


}
