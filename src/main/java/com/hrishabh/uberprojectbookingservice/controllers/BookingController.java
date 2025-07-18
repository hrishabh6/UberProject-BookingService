package com.hrishabh.uberprojectbookingservice.controllers;


import com.hrishabh.uberprojectbookingservice.dto.CreateBookingDto;
import com.hrishabh.uberprojectbookingservice.dto.CreateBookingResponseDto;
import com.hrishabh.uberprojectbookingservice.dto.UpdateBookingResponseDto;
import com.hrishabh.uberprojectbookingservice.dto.UpdateBookingRequestDto;
import com.hrishabh.uberprojectbookingservice.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto createBookingDto){
        CreateBookingResponseDto responseDto = bookingService.createBooking(createBookingDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateBookingRequestDto requestDto, @PathVariable Long bookingId){
        return new ResponseEntity<>(bookingService.updateBooking(requestDto, bookingId), HttpStatus.OK);
    }


}
