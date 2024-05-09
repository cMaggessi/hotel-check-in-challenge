package com.hotelcheckin.hotel.service;
import org.springframework.stereotype.Service;

import com.hotelcheckin.hotel.model.Reservation;
import com.hotelcheckin.hotel.model.ReservationRequest;


@Service
public interface CheckInService {
    Reservation makeReservation(ReservationRequest request);




}
