package it.epicode.u5w3day2PRATICA.Service;

import com.cloudinary.Cloudinary;
import it.epicode.u5w3day2PRATICA.Dto.ViaggioDto;
import it.epicode.u5w3day2PRATICA.Enum.StatoViaggio;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.Viaggio;
import it.epicode.u5w3day2PRATICA.Repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViaggioService {
    @Autowired
    ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    JavaMailSenderImpl javaMailSender;

    //Metodo save viaggio
    public Viaggio saveViaggio(ViaggioDto viaggioDto) throws NotFoundException {
        Viaggio viaggio= new Viaggio();
        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setDataViaggio(viaggioDto.getDataViaggio());

        //setto la mia email per farmi arrivare la conferma
        sendMail("lucaferr95@gmail.com");
        return viaggioRepository.save(viaggio);
    }
    //metodo get all viaggi
    public List<Viaggio> getAllViaggi(){
    return viaggioRepository.findAll();
    }

    //metodo get viaggio

    public Viaggio getViaggio(int id) throws NotFoundException{
        return viaggioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaggio con id " + id + " non trovato"));
    }

    //metodo update
    public Viaggio updateViaggio(int id, ViaggioDto viaggioDto) throws NotFoundException {
        Viaggio viaggioDaAggiornare= getViaggio(id);
        viaggioDaAggiornare.setDestinazione(viaggioDto.getDestinazione());
        viaggioDaAggiornare.setDataViaggio(viaggioDto.getDataViaggio());
        return viaggioRepository.save(viaggioDaAggiornare);
    }
    //metodo delete
    public void deleteViaggio(int id) throws NotFoundException{
        Viaggio viaggioDaCancellare= getViaggio(id);
        viaggioRepository.delete(viaggioDaCancellare);
    }
    //metodo patch per cambiare lo stato del viaggio

    public Viaggio aggiornaStato(int id, StatoViaggio nuovoStato) throws NotFoundException {
        Viaggio viaggioDaAggiornare = viaggioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaggio non trovato"));

        viaggioDaAggiornare.setStatoViaggio(nuovoStato);
        return viaggioRepository.save(viaggioDaAggiornare);
    }


    //invio email

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo, SIUUUUMMMMM");

        javaMailSender.send(message);
    }
}
