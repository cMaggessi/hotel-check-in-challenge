package com.hotelcheckin.hotel.controller;


import com.hotelcheckin.hotel.model.Reservation;
import com.hotelcheckin.hotel.model.ReservationRequest;
import com.hotelcheckin.hotel.service.CheckInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Object> makeReservation(@RequestBody ReservationRequest request) {
        System.out.println("OI");
        try {
            Reservation reservation = checkInService.makeReservation(request);
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("HELLO");
    }
}