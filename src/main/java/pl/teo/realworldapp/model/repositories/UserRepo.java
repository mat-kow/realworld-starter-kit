package pl.teo.realworldapp.model.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.teo.realworldapp.model.entity.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    boolean existsByEmailOrUsername(String username, String email);
    Optional<User> findUserByEmailIgnoreCase(String email);
    Optional<User> findUserByUsername(String username);
}
