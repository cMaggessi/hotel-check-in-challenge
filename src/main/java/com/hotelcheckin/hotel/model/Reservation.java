package com.hotelcheckin.hotel.model;


import lombok.Data;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;


@Data
public class Reservation {
    private int reservationNumber;
    private String clientName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String roomType;
    private double totalAmount;

    public Reservation(int reservationNumber, String clientName, LocalDate checkInDate, LocalDate checkOutDate, String roomType, double totalAmount) {
        this.reservationNumber = reservationNumber;
        this.clientName = clientName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomType = roomType;
        this.totalAmount = totalAmount;
    }

}
