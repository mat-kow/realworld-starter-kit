package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.entity.User;
import pl.teo.realworldapp.model.dto.*;

public interface UserService {
    UserAuthenticationDto register(UserRegisterDto registerDto);
    UserAuthenticationDto getCurrentDto();
    User getCurrentUser();
    UserAuthenticationDto update(UserUpdateDto userUpdateDto);
    UserAuthenticationDto login(UserLoginDto userLoginDto);
    Profile getProfileByUsername(String username);
    Profile followProfile(String profileName);
    Profile unfollowProfile(String username);

    void update(User user);
}
