package com.hotelcheckin.hotel.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    @Size(min = 5, max = 100, message = "Nome do cliente não pode ser vazio, deve ter entre 5 a 100 caracteres")
    private String clientName;


    @NotEmpty(message = "Tipo de quarto inválido.")
    @NotNull(message = "Tipo de quarto inválido.")
    private String roomType;

    @Valid
    @Future(message = "A data de check-out não pode ser no mesmo dia do check-in.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Data não pode ser vazia!")
    private LocalDate checkOutDate;


}
