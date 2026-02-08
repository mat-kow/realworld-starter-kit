package pl.teo.realworldapp.controllers;

import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.Profile;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @PostMapping("/{username}/follow")
    public Profile followProfile(@RequestBody Profile profile, @PathVariable String username) {
        return profile;
    }
}
