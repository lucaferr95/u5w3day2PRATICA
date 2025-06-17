package it.epicode.u5w3day2PRATICA.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message = "L'email non può essere vuota")
    private String email;
    @NotEmpty(message = "La password non può essere vuota")
    private String password;
}
