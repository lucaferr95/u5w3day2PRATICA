package it.epicode.u5w3day2PRATICA.Controller;


import it.epicode.u5w3day2PRATICA.Dto.DipendenteDto;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.Dipendente;
import it.epicode.u5w3day2PRATICA.Service.DipendenteService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    //metodo Post
    @PostMapping("")
    @PreAuthorize(("hasAuthority('ADMIN')")) //hasauthority autorizza un solo ruolo ad accedere all'endpoint
    @ResponseStatus(HttpStatus.CREATED)
    //per gestire la validazione, devo aggiungere @Validated al dto e poi il metodo deve gestire anche il
    //parametro di tipo BindingResult che conterrà tutti gli eventuali errori del dto
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteDto dipendenteDto,
                                     BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            //gli errori li prendiamo dal bindingResult ma vengono restituiti come liste di objectError.
            //il costruttore dell'eccezione accetta una stringa e quindi con lo stream trasformiamo la lista in una stringa
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return dipendenteService.saveDipendente(dipendenteDto);
    }

    //metodo get all

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")//Autorizza l0utilizzo dell'endpoint da parte di piu ruoli
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy){
        return dipendenteService.getAllDipendenti(page, size, sortBy);
    }

    //metodo get singolo

    @GetMapping("/{id}")
    public Dipendente getDipendente(@PathVariable int id) throws NotFoundException {
        return dipendenteService.getDipendente(id);
    }

    //metodo put

    @PutMapping("/{id}")
    public Dipendente updateDipendente (@PathVariable int id, @RequestBody @Validated
                                        DipendenteDto dipendenteDto, BindingResult bindingResult)
                                        throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return dipendenteService.updateDipendente(id, dipendenteDto);
    }
        //metodo delete
    @DeleteMapping("/{id}")
    public void deleteDipendente(@PathVariable int id) throws NotFoundException {
            dipendenteService.deleteDipendente(id);
        }
        //metodo patch
    @PatchMapping("/{id}")
    public String patchDipendente(@PathVariable int id, @RequestBody MultipartFile file) throws NotFoundException,
                                                  IOException {
       return dipendenteService.patchDipendente(id, file);
    }
}
