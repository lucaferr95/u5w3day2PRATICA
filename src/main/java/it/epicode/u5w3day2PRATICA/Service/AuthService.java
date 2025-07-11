package it.epicode.u5w3day2PRATICA.Service;

import it.epicode.u5w3day2PRATICA.Dto.LoginDto;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.User;
import it.epicode.u5w3day2PRATICA.Repository.UserRepository;
import it.epicode.u5w3day2PRATICA.Security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
        1. verificare che l'utente esiste
        2. se l'utente non esite, lancia una eccezione
        3. se l'utente esiste, generare il token e inviarlo al client
         */

    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByEmail(loginDto.getEmail()).
                orElseThrow(() -> new NotFoundException("Utente con questo username/password non trovato"));

        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            //utente è autenticato, devo creare il token
            return jwtTool.createToken(user);
        }
        else{
            throw new NotFoundException("Utente con questo username/password non trovato");
        }
    }
}