package com.hotelcheckin.hotel.service;

import com.hotelcheckin.hotel.model.Room;
import com.hotelcheckin.hotel.model.Reservation;
import com.hotelcheckin.hotel.model.ReservationRequest;
import com.hotelcheckin.hotel.model.RoomType;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CheckInServiceImpl implements CheckInService {

    private List<Room> rooms = new ArrayList<>();

    private List<Reservation> reservations = new ArrayList<>();

    private AtomicInteger reservationNumber = new AtomicInteger(1);


    //Aqui chamamos um método nativo do Atomic Integer chamado getAndIncrement, que incrementa o número atual +1 (fonte docs.oracle.com)
    int incrt = reservationNumber.getAndIncrement();


    public CheckInServiceImpl() {
        rooms.add(new Room(101, RoomType.SINGLE, true));
        rooms.add(new Room(102, RoomType.COMPANION, true));
        rooms.add(new Room(103, RoomType.FAMILY, true));
    }

    @Override
    public Reservation makeReservation(ReservationRequest request) {
        // Implementação da lógica de reserva

        Room dispRoom = findAvailableRoom(RoomType.valueOf(request.getRoomType()), request.getCheckOutDate());

        if (dispRoom == null) {
            throw new RuntimeException("Não há quartos disponíveis para o tipo de quarto e datas solicitadas.");
        }

        // Calcula o valor total da reserva
        double totalAmount = calculateTotalAmount(request.getRoomType(), request.getCheckOutDate());

        //Criar a reserva

        Reservation reservation = new Reservation(
                incrt,
                request.getClientName(),
                LocalDate.now(),
                request.getCheckOutDate(),
                request.getRoomType(),
                totalAmount
        );
        reservations.add(reservation);


        //Marcando quarto como indisponível após checkin.
        dispRoom.setAvailable(false);

        return reservation;
    }

    private Room findAvailableRoom(RoomType roomType, LocalDate checkOutDate) {
        for (Room room : rooms) {
            if (room.getRoomType().equals(roomType) && room.isAvailable()) {
                boolean isDisp = true;
                for (Reservation reservation : reservations) {
                    if (reservation.getRoomType().equals(roomType) && reservation.getCheckInDate().isBefore(checkOutDate)) {
                        LocalDate.now();
                    }
                }
                if (isDisp) {
                    return room;
                }
            }
        }
        return null;
    }

    private double calculateTotalAmount(String roomType, LocalDate checkOutDate) {
        double dailyRate = getDailyRate(roomType, checkOutDate);
        long nights = ChronoUnit.DAYS.between(LocalDate.now(), checkOutDate);
        return dailyRate * nights;
    }

    private double getDailyRate(String roomType, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        if (roomType.equals("SINGLE")) {
            return (dayOfWeek >= 5) ? 120 : 100;
        } else if (roomType.equals("COMPANION")) {
            return (dayOfWeek >= 5) ? 150 : 130;
        } else if (roomType.equals("FAMILY")) {
            return (dayOfWeek >= 5) ? 180 : 160;
        } else {
            throw new IllegalArgumentException("Tipo de quarto inválido.");
        }
    }
}