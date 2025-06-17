package it.epicode.u5w3day2PRATICA.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class DipendenteDto {
    @NotEmpty(message = "Il campo nome non può essere nullo o vuoto")
    private String nome;
    @NotEmpty(message = "Il campo cognome non può essere nullo o vuoto")
    private String cognome;
    @NotEmpty(message = "Il campo username non può essere nullo o vuoto")
    private String username;
    @NotNull(message = "la data di nascita non può essere nulla")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascita;

    @Email(message = "l'email deve avere un formato valido")
    private String email;
}
