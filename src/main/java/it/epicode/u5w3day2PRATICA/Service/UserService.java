package it.epicode.u5w3day2PRATICA.Service;

import it.epicode.u5w3day2PRATICA.Dto.UserDto;
import it.epicode.u5w3day2PRATICA.Enum.Role;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.User;
import it.epicode.u5w3day2PRATICA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        //la password in chiaro che si trova nel dto verrà passata come parametro al metodo encode dell'encoder
        //Bcrypt codificherà la password e genererà l'hash. Questo hash viene settato sullo user e salvato sul db

        user.setPassword(passwordEncoder.encode(userDto.getPassword())); //questo metodo andrà applicare il Bcript sulla password in chiaro
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public List<User> getAllUser() {

        return userRepository.findAll();
    }

    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato"));
    }

    public User updateUser(int id, UserDto userDto) throws NotFoundException {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setEmail(userDto.getEmail());
        userDaAggiornare.setPassword(userDto.getPassword());
        //matches va a verificare se hanno la stessa password; gli passiamo prima la
        // password in chiaro e poi la password codificata
        //se non sono uguali, la deve aggiornare
        if(!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())) {
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id) throws NotFoundException {
        User userDaCancellare = getUser(id);

        userRepository.delete(userDaCancellare);
    }
}