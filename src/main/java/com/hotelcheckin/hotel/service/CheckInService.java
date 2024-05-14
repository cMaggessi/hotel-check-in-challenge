package com.hotelcheckin.hotel.service;
import com.hotelcheckin.hotel.model.Room;
import org.springframework.stereotype.Service;

import com.hotelcheckin.hotel.model.Reservation;
import com.hotelcheckin.hotel.model.ReservationRequest;

import java.util.List;


@Service
public interface CheckInService {
    Reservation makeReservation(ReservationRequest request);


    List<Room> getAllRooms();
}
