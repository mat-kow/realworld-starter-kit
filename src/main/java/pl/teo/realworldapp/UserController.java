package pl.teo.realworldapp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.User;
import pl.teo.realworldapp.repositories.UserRepo;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserRepo userRepo;

    //TODO use Spring Security
    @PostMapping("/users")
    public Map<String, User> register(@RequestBody User user) {
        //TODO encrypt password
        return getUserMapWrapper(userRepo.save(user));
    }

    @GetMapping("/user")
    public Map<String, User> getUser() {
        return getUserMapWrapper(getCurrentUser());
    }

    @PutMapping("/user")
    public Map<String, User> updateUser(@RequestBody User user) {
        User currentUser = getCurrentUser();

        if (user.getUsername() != null) currentUser.setUsername(user.getUsername());
        if (user.getEmail() != null) currentUser.setEmail(user.getEmail());
        //todo encrypt
        if (user.getPassword() != null) currentUser.setPassword(user.getPassword());
        if (user.getBio() != null) currentUser.setBio(user.getBio());
        if (user.getImage() != null) currentUser.setImage(user.getImage());

        return getUserMapWrapper(userRepo.save(currentUser));
    }

    private User getCurrentUser() {
        //todo get it from JWT
        return new User("qw","qw",null);
    }

    private Map<String, User> getUserMapWrapper(User user) {
        Map<String, User> map = new HashMap<>();
        map.put("user", user);
        return map;
    }
}
