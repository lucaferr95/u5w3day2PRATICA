package it.epicode.u5w3day2PRATICA.Repository;


import it.epicode.u5w3day2PRATICA.Model.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {
  Optional <User> findByEmail(@NotEmpty(message = "L'username non può essere vuoto") String email);
}
