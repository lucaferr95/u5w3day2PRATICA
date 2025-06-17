package it.epicode.u5w3day2PRATICA.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "il nome non può essere vuoto")
    private String nome;
    @NotEmpty(message = "il cognome non può essere vuoto")
    private String cognome;
    @NotEmpty(message = "L'email non può essere vuota")
    private String email;
    @NotEmpty(message = "La password non può essere vuota")
    private String password;


}
