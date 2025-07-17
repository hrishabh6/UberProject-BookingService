package com.hrishabh.uberprojectbookingservice.service;

import com.example.uberprojectentityservice.models.Booking;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingDto;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingResponseDto;
import com.hrishabh.uberprojectbookingservice.dto.UpdateBookingRequestDto;
import com.hrishabh.uberprojectbookingservice.dto.UpdateBookingResponseDto;

public interface BookingService {

    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);

    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto requestDto, Long bookingId);

}
