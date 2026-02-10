package pl.teo.realworldapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.teo.realworldapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmailOrUsername(String username, String email);
    Optional<User> findUserByEmailIgnoreCase(String email);
}
