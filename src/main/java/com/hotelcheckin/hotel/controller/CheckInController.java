package com.hotelcheckin.hotel.controller;


import com.hotelcheckin.hotel.exception.InvalidDateException;
import com.hotelcheckin.hotel.exception.InvalidRoomTypeException;
import com.hotelcheckin.hotel.model.Reservation;
import com.hotelcheckin.hotel.model.ReservationRequest;
import com.hotelcheckin.hotel.model.Room;
import com.hotelcheckin.hotel.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Object> makeReservation(@Valid @RequestBody ReservationRequest request) {

        try {
            Reservation reservation = checkInService.makeReservation(request);
            return ResponseEntity.ok(reservation);
        }
        catch (RuntimeException e) {
            if(e.getMessage().contains("No enum constant")) {
                throw new InvalidRoomTypeException("Tipo de quarto inválido.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = checkInService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("HELLO");
    }
}
