package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.UserLoginDto;
import pl.teo.realworldapp.model.dto.UserRegisterDto;
import pl.teo.realworldapp.model.dto.UserUpdateDto;
import pl.teo.realworldapp.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/users")
    public Map<String, Object> register(@RequestBody UserRegisterDto user) {
        return getUserMapWrapper(userService.register(user));
    }

    @PostMapping("/users/login")
    public Map<String, Object> login(@RequestBody UserLoginDto loginDto) {
        return getUserMapWrapper(userService.login(loginDto));
    }

    @GetMapping("/user")
    public Map<String, Object> getUser(Principal principal) {
        return getUserMapWrapper(userService.getCurrent(principal));
    }

    @PutMapping("/user")
    public Map<String, Object> updateUser(@RequestBody UserUpdateDto userUpdateDto, Principal principal) {
        return getUserMapWrapper(userService.update(userUpdateDto, principal));
    }

    private Map<String, Object> getUserMapWrapper(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", object);
        return map;
    }
}
