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

    private final List<Room> rooms = new ArrayList<>();

    private final List<Reservation> reservations = new ArrayList<>();

    private AtomicInteger reservationNumber = new AtomicInteger(1);


    //Aqui chamamos um método nativo do Atomic Integer chamado getAndIncrement, que incrementa o número atual +1 (fonte docs.oracle.com)
    int incrt = reservationNumber.getAndIncrement();


    public CheckInServiceImpl() {
        rooms.add(new Room(101, RoomType.SINGLE, true));
        rooms.add(new Room(102, RoomType.COMPANION, true));
        rooms.add(new Room(103, RoomType.FAMILY, true));
    }


    @Override
    public List<Room> getAllRooms() {
         return rooms;
    }

    @Override
    public Reservation makeReservation(ReservationRequest request) {
        // Implementação da lógica de reserva

        Room dispRoom = findAvailableRoom(RoomType.valueOf(String.valueOf(request.getRoomType())), request.getCheckOutDate());

        if (dispRoom == null) {
            throw new RuntimeException("Não há quartos disponíveis para o tipo de quarto e datas solicitadas.");
        }

        // Calcula o valor total da reserva
        double totalAmount = calculateTotalAmount(RoomType.valueOf(request.getRoomType()), request.getCheckOutDate());

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
        boolean isDisp;
        for (Room room : rooms) {
            if (room.getRoomType().equals(roomType) && room.isAvailable()) {
               isDisp = true;
                for (Reservation reservation : reservations) {
                    if (reservation.getCheckInDate().isBefore(checkOutDate)) {
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

    private double calculateTotalAmount(RoomType roomType, LocalDate checkOutDate) {
        double dailyRate = 0;
        //Assumindo que a data do checkIn é a mesma quando disparamos a requisição
        LocalDate checkInDate = LocalDate.now();
        // Quantos dias entre checkIn e checkOut
        long numOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);


        //Aqui, conforme sugerido, calculamos o preço de cada dia (dailyRate)
        for(int i = 0 ; i< numOfDays ; i++) {
            dailyRate += getDailyRate(roomType, checkInDate.plusDays(i));
        }

        return dailyRate;
    }

    private double getDailyRate(RoomType roomType, LocalDate date) {
// Taxa de final de semana é 20% então WEEKEND_TAX = 1.2
        final double WEEKEND_TAX = 1.2;

        int dayOfWeek = date.getDayOfWeek().getValue();

        //Sugestão para escaparmos do hard coding, obrigado!


      if (roomType.equals(RoomType.SINGLE)) {
            return (dayOfWeek >= 5) ? roomType.getPrice()*WEEKEND_TAX : roomType.getPrice();
        } else if (roomType.equals(RoomType.COMPANION)) {
          return (dayOfWeek >= 5) ? roomType.getPrice()*WEEKEND_TAX : roomType.getPrice();
        } else if (roomType.equals(RoomType.FAMILY)) {
          return (dayOfWeek >= 5) ? roomType.getPrice()*WEEKEND_TAX : roomType.getPrice();
        } else {
            throw new IllegalArgumentException("Tipo de quarto inválido.");
        }
    }
}