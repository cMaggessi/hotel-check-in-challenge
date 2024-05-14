package com.hotelcheckin.hotel.model;

import java.time.LocalDate;

public enum RoomType {
    SINGLE(100),
    COMPANION(130),
    FAMILY(140);

    private final double price;

    RoomType(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }


}