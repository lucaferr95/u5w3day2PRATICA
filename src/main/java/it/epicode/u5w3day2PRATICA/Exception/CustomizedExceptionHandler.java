package it.epicode.u5w3day2PRATICA.Exception;


import it.epicode.u5w3day2PRATICA.Model.ApiError;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice  //controller che gestisce gli errori
public class CustomizedExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class) //serve per mappare il metodo che gestisce questa eccezione
    public ApiError notFoundExceptionHandler(NotFoundException e) {
        ApiError error= new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//serve per mappare il metodo che gestisce questa eccezione
    public ApiError validationExceptionHandler(ValidationException e) {
        ApiError error= new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }
    //Aggiungo l'eccezione personalizzata per le prenotazioni
    @ExceptionHandler(PrenotazioneGiaEsistenteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePrenotazioneGiaEsistente(PrenotazioneGiaEsistenteException e) {
        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }

}