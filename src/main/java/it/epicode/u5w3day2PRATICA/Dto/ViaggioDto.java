package it.epicode.u5w3day2PRATICA.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ViaggioDto {
    @NotEmpty(message = "Il campo destinazione non può essere nullo o vuoto")
    private String destinazione;
    @NotNull(message = "la data del viaggio non può essere nulla")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataViaggio;

}
