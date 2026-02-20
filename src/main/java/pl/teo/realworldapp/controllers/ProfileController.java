package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.Profile;
import pl.teo.realworldapp.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Profile>> getProfile(@PathVariable String username) {
        Profile profile = userService.getProfileByUsername(username);
        return getProfileMapWrapper(profile);
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<Map<String, Profile>> followProfile(@PathVariable String username) {
        return getProfileMapWrapper(userService.followProfile(username));
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<Map<String, Profile>> unfollowProfile(@PathVariable String username) {
        return getProfileMapWrapper(userService.unfollowProfile(username));
    }

    private ResponseEntity<Map<String, Profile>> getProfileMapWrapper(Profile profile) {
        return ResponseEntity.ok(Map.of("user", profile));
    }

}
