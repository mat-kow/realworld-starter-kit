package pl.teo.realworldapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.teo.realworldapp.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
