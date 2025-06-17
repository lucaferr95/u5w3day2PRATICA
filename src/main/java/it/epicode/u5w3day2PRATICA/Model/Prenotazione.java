package it.epicode.u5w3day2PRATICA.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class Prenotazione {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate dataRichiesta;
    private String preferenze;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;
}
