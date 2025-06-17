package it.epicode.u5w3day2PRATICA.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Dipendente {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String cognome;
    private String username;
    private LocalDate dataNascita;
    private String email;
    private String urlImmagine;

    @JsonIgnore
    @OneToMany(mappedBy = "dipendente")
    private List <Prenotazione> prenotazioni;
}
