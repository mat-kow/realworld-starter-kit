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
    public Map<String, Object> login(@RequestBody @Valid UserLoginDto loginDto) {
        return getUserMapWrapper(userService.login(loginDto));
    }

    @GetMapping("/user")
    public Map<String, Object> getUser() {
        return getUserMapWrapper(userService.getCurrentDto());
    }

    @PutMapping("/user")
    public Map<String, Object> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return getUserMapWrapper(userService.update(userUpdateDto));
    }

    private Map<String, Object> getUserMapWrapper(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", object);
        return map;
    }
}
