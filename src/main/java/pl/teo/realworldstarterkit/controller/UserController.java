package pl.teo.realworldstarterkit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldstarterkit.app.jwt.JwtBuilder;
import pl.teo.realworldstarterkit.model.dto.*;
import pl.teo.realworldstarterkit.model.entity.User;
import pl.teo.realworldstarterkit.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users/login")
    public UserAuthenticationDto login(@RequestBody UserLoginDto dto) {
        return userService.login(dto);
    }

    @PostMapping("/users")
    public UserAuthenticationDto register(@RequestBody UserRegistrationDto dto) {
        return userService.save(dto);
    }

    @GetMapping("/user")
    public UserAuthenticationDto getCurrentUser(Principal principal,
                                                @RequestHeader("Authorization") String token) {
        UserAuthenticationDto user = userService.getUser(principal);
        user.setToken(token);
        return user;
    }

    @PutMapping("/user")
    public UserAuthenticationDto update(Principal principal,
                                        @RequestBody UserUpdateDto userUpdateDto,
                                        @RequestHeader("Authorization") String token) {
        UserAuthenticationDto user = userService.update(userUpdateDto, principal);
        user.setToken(token.replaceFirst("_", " "));
        return user;
    }


}
