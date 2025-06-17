package it.epicode.u5w3day2PRATICA.Controller;

import it.epicode.u5w3day2PRATICA.Dto.LoginDto;
import it.epicode.u5w3day2PRATICA.Dto.UserDto;
import it.epicode.u5w3day2PRATICA.Exception.NotFoundException;
import it.epicode.u5w3day2PRATICA.Model.User;
import it.epicode.u5w3day2PRATICA.Service.AuthService;
import it.epicode.u5w3day2PRATICA.Service.UserService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }

        return userService.saveUser(userDto);
    }
    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult)
            throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e));
        }

        return authService.login(loginDto); // Questo deve restituire il token
    }

}