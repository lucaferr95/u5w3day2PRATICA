package it.epicode.u5w3day2PRATICA.Repository;


import it.epicode.u5w3day2PRATICA.Model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository <Prenotazione, Integer> {
    //aggiungo questo metodo per evitare che il dipendente faccia piu prenotazioni nello stesso giorno
    Optional<Prenotazione> findByDipendenteIdAndDataRichiesta(int dipendenteId, LocalDate dataRichiesta);

}
