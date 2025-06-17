package it.epicode.u5w3day2PRATICA.Service;

import com.cloudinary.Cloudinary;
import it.epicode.u5w3day2PRATICA.Dto.DipendenteDto;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.Dipendente;
import it.epicode.u5w3day2PRATICA.Repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    //Metodo Save
    public Dipendente saveDipendente(DipendenteDto dipendenteDto) {
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setEmail(dipendenteDto.getEmail());
        dipendente.setUsername(dipendenteDto.getUsername());
        dipendente.setDataNascita(dipendenteDto.getDataNascita());
        //setto la mia email per farmi arrivare la conferma
//        sendMail("lucaferr95@gmail.com");

        return dipendenteRepository.save(dipendente);
    }

    //Metodo getAll con paginazione

    public Page<Dipendente> getAllDipendenti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    //Metodo get dipendente

    public Dipendente getDipendente(int id) throws NotFoundException {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dipendente con id: " + id + " non trovato"));
    }

    //Metodo update
    public Dipendente updateDipendente(int id, DipendenteDto dipendenteDto) throws NotFoundException {
        Dipendente dipendenteDaAggiornare = getDipendente(id);
        dipendenteDaAggiornare.setNome(dipendenteDto.getNome());
        dipendenteDaAggiornare.setCognome(dipendenteDto.getCognome());
        dipendenteDaAggiornare.setEmail(dipendenteDto.getEmail());
        dipendenteDaAggiornare.setUsername(dipendenteDto.getUsername());
        dipendenteDaAggiornare.setDataNascita(dipendenteDto.getDataNascita());
        return dipendenteRepository.save(dipendenteDaAggiornare);

    }

    //metodo delete dipendente
    public void deleteDipendente(int id) throws NotFoundException {
        Dipendente dipendenteDaRimuovere = getDipendente(id);
        dipendenteRepository.delete(dipendenteDaRimuovere);
    }

    //metodo patch

    public String patchDipendente(int id, MultipartFile file) throws NotFoundException, IOException {
        Dipendente dipendenteDaPatchare = getDipendente(id);
        //salvo il file su cloudinary e ricevo l'url del file che si trova su cloudinary
        String url = (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");
        dipendenteDaPatchare.setUrlImmagine(url);
        dipendenteRepository.save(dipendenteDaPatchare);
        return url;
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