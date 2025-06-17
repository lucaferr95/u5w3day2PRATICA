package it.epicode.u5w3day2PRATICA.Controller;

import it.epicode.u5w3day2PRATICA.Dto.ViaggioDto;
import it.epicode.u5w3day2PRATICA.Enum.StatoViaggio;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.Viaggio;
import it.epicode.u5w3day2PRATICA.Service.ViaggioService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/viaggi")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    //metodo Post
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    //per gestire la validazione, devo aggiungere @Validated al dto e poi il metodo deve gestire anche il
    //parametro di tipo BindingResult che conterrà tutti gli eventuali errori del dto
    public Viaggio viaggio(@RequestBody @Validated ViaggioDto viaggioDto,
                           BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            //gli errori li prendiamo dal bindingResult ma vengono restituiti come liste di objectError.
            //il costruttore dell'eccezione accetta una stringa e quindi con lo stream trasformiamo la lista in una stringa
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return viaggioService.saveViaggio(viaggioDto);
    }

    //metodo get all

    @GetMapping("")
    public List<Viaggio> getAllViaggi(){
        return viaggioService.getAllViaggi();
    }

    //metodo get singolo

    @GetMapping("/{id}")
    public Viaggio getViaggio(@PathVariable int id) throws NotFoundException {
        return viaggioService.getViaggio(id);
    }

    //metodo put

    @PutMapping("/{id}")
    public Viaggio updateViaggio (@PathVariable int id, @RequestBody @Validated
    ViaggioDto viaggioDto, BindingResult bindingResult)
            throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return viaggioService.updateViaggio(id, viaggioDto);
    }
    //metodo delete
    @DeleteMapping("/{id}")
    public void deleteViaggio(@PathVariable int id) throws NotFoundException {
        viaggioService.deleteViaggio(id);
    }
    //metodo patch per aggiornare lo stato del viaggio
    @PatchMapping("/{id}/stato")
    public Viaggio patchStatoViaggio(@PathVariable int id,
                                     @RequestBody String nuovoStato) throws NotFoundException {
        StatoViaggio stato = StatoViaggio.valueOf(nuovoStato.toUpperCase());
        return viaggioService.aggiornaStato(id, stato);
    }

}
