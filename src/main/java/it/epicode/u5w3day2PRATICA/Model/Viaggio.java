package it.epicode.u5w3day2PRATICA.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.u5w3day2PRATICA.Enum.StatoViaggio;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Viaggio {
    @Id
    @GeneratedValue
    private int id;
    private String destinazione;
    private LocalDate dataViaggio;

    @Enumerated(EnumType.STRING)
    private StatoViaggio statoViaggio;

    @OneToMany(mappedBy = "viaggio")
    @JsonIgnore
    private List <Prenotazione> prenotazioni;

}
