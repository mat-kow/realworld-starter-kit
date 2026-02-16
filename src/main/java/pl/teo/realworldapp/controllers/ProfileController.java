package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.Profile;
import pl.teo.realworldapp.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/{username}")
    public Map<String, Profile> getProfile(@PathVariable String username) {
        Profile profile = userService.getProfileByUsername(username);
        return getProfileMapWrapper(profile);
    }

    @PostMapping("/{username}/follow")
    public Map<String, Profile> followProfile(@PathVariable String username, Principal principal) {
        return getProfileMapWrapper(userService.followProfile(username, principal));
    }

    @DeleteMapping("/{username}/follow")
    public Map<String, Profile> unfollowProfile(@PathVariable String username, Principal principal) {
        return getProfileMapWrapper(userService.unfollowProfile(username, principal));
    }

    private Map<String, Profile> getProfileMapWrapper(Profile profile) {
        Map<String, Profile> map = new HashMap<>();
        map.put("user", profile);
        return map;
    }

}
