package it.epicode.u5w3day2PRATICA.Service;

import com.cloudinary.Cloudinary;
import it.epicode.u5w3day2PRATICA.Dto.PrenotazioneDto;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Exception.PrenotazioneGiaEsistenteException;
import it.epicode.u5w3day2PRATICA.Model.Dipendente;
import it.epicode.u5w3day2PRATICA.Model.Prenotazione;
import it.epicode.u5w3day2PRATICA.Model.Viaggio;
import it.epicode.u5w3day2PRATICA.Repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private ViaggioService viaggioService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private DipendenteService dipendenteService;
    //metodo save
    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException, PrenotazioneGiaEsistenteException {
        Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());
        // Verifica se esiste già una prenotazione per quel dipendente e quella data
        Optional<Prenotazione> prenotazioneEsistente = prenotazioneRepository
                .findByDipendenteIdAndDataRichiesta(prenotazioneDto.getDipendenteId(), prenotazioneDto.getDataRichiesta());

        if (prenotazioneEsistente.isPresent()) {
            throw new PrenotazioneGiaEsistenteException("Hai già una prenotazione per questa data");
        }


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazione.setViaggio(viaggio);

        //recupero il dipendente

        Dipendente dipendente = dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
        prenotazione.setDipendente(dipendente);

        //setto la mia email per farmi arrivare la conferma
        sendMail("lucaferr95@gmail.com");
        return prenotazioneRepository.save(prenotazione);
    }

    //metodo get prenotazioni
    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }

    //metodo get singola prenotazione
    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione con id " + id + " non trovata"));
    }

    //metodo update Prenotazione
    public Prenotazione updatePrenotazione(int id, PrenotazioneDto prenotazioneDto) throws NotFoundException {

        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);
        prenotazioneDaAggiornare.setDataRichiesta(prenotazioneDto.getDataRichiesta());

          /*
        nel dto io ho anche viaggioId, quindi devo verificare se l'id del viaggio collegato alla prenotazione
        sul db è diverso dall'id dell'università nel dto.
         */

        if (prenotazioneDaAggiornare.getViaggio().getId() != prenotazioneDto.getViaggioId()) {
            Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());
            prenotazioneDaAggiornare.setViaggio(viaggio);
        }

        return prenotazioneRepository.save(prenotazioneDaAggiornare);
    }

    //delete prenotazioni
    public void deletePrenotazione(int id) throws NotFoundException {
        Prenotazione prenotazioneDaCancellare = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneDaCancellare);
    }

    //invio email
    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }


}
