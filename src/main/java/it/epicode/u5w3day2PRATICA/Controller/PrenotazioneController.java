package it.epicode.u5w3day2PRATICA.Controller;



import it.epicode.u5w3day2PRATICA.Dto.PrenotazioneDto;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Exception.PrenotazioneGiaEsistenteException;
import it.epicode.u5w3day2PRATICA.Model.Prenotazione;
import it.epicode.u5w3day2PRATICA.Repository.PrenotazioneRepository;
import it.epicode.u5w3day2PRATICA.Service.PrenotazioneService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    //metodo Post
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    //per gestire la validazione, devo aggiungere @Validated al dto e poi il metodo deve gestire anche il
    //parametro di tipo BindingResult che conterrà tutti gli eventuali errori del dto
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto,
                                         BindingResult bindingResult) throws NotFoundException, ValidationException, PrenotazioneGiaEsistenteException {
        if(bindingResult.hasErrors()){
            //gli errori li prendiamo dal bindingResult ma vengono restituiti come liste di objectError.
            //il costruttore dell'eccezione accetta una stringa e quindi con lo stream trasformiamo la lista in una stringa
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    //metodo get all

    @GetMapping("")
    public List<Prenotazione> getAllPrenotazioni(){
      return   prenotazioneService.getAllPrenotazioni();
    }

    //metodo get singolo

    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable int id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    //metodo put

    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione (@PathVariable int id, @RequestBody @Validated
    PrenotazioneDto prenotazioneDto, BindingResult bindingResult)
            throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return prenotazioneService.updatePrenotazione(id, prenotazioneDto);
    }
    //metodo delete
    @DeleteMapping("/{id}")
    public void deletePrenotazione(@PathVariable int id) throws NotFoundException {
        prenotazioneService.deletePrenotazione(id);
    }
    //metodo patch solo per cambiare le preferenze
    @PatchMapping("/{id}/preferenze")
    public Prenotazione patchPreferenze(@PathVariable int id, @RequestBody Map<String, String> body)
            throws NotFoundException {
        String nuovePreferenze = body.get("preferenze");
        Prenotazione prenotazione = prenotazioneService.getPrenotazione(id);
        prenotazione.setPreferenze(nuovePreferenze);
        return prenotazioneRepository.save(prenotazione);
    }

}

