package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.Profile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

//    @GetMapping("/{username}")
//    public Map<String, Profile> getProfile(@PathVariable String username) {
//        Profile profile = profileRepo.getProfileByUsername(username).orElseThrow(RuntimeException::new);
//        return getProfileMapWrapper(profile);
//    }
//
//    @PostMapping("/{username}/follow")
//    public Map<String, Profile> followProfile(@RequestBody Profile profile, @PathVariable String username) {
//
//    }


    private Map<String, Profile> getProfileMapWrapper(Profile profile) {
        Map<String, Profile> map = new HashMap<>();
        map.put("user", profile);
        return map;
    }

}
