package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.User;
import pl.teo.realworldapp.model.dto.*;

import java.security.Principal;

public interface UserService {
    UserAuthenticationDto register(UserRegisterDto registerDto);
    UserAuthenticationDto getCurrentDto(Principal principal);
    User getCurrentUser(Principal principal);
    UserAuthenticationDto update(UserUpdateDto userUpdateDto, Principal principal);
    UserAuthenticationDto login(UserLoginDto userLoginDto);
    Profile getProfileByUsername(String username);
    Profile followProfile(String profileName, Principal principal);
    Profile unfollowProfile(String username, Principal principal);
}
