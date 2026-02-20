package pl.teo.realworldapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.UserLoginDto;
import pl.teo.realworldapp.model.dto.UserRegisterDto;
import pl.teo.realworldapp.model.dto.UserUpdateDto;
import pl.teo.realworldapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid UserRegisterDto user) {
        return ResponseEntity.status(201).body(getUserMapWrapper(userService.register(user)));
    }

    @PostMapping("/users/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid UserLoginDto loginDto) {
        return ResponseEntity.ok(getUserMapWrapper(userService.login(loginDto)));
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUser() {
        return ResponseEntity.ok(getUserMapWrapper(userService.getCurrentDto()));
    }

    @PutMapping("/user")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(getUserMapWrapper(userService.update(userUpdateDto)));
    }

    private Map<String, Object> getUserMapWrapper(Object object) {
        return Map.of("user", object);
    }
}
