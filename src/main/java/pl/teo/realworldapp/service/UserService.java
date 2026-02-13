package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.User;
import pl.teo.realworldapp.model.dto.UserLoginDto;
import pl.teo.realworldapp.model.dto.UserRegisterDto;
import pl.teo.realworldapp.model.dto.UserUpdateDto;
import pl.teo.realworldapp.model.dto.UserAuthenticationDto;

import java.security.Principal;

public interface UserService {
    UserAuthenticationDto register(UserRegisterDto registerDto);
    UserAuthenticationDto getCurrentDto(Principal principal);
    User getCurrentUser(Principal principal);
    UserAuthenticationDto update(UserUpdateDto userUpdateDto, Principal principal);
    UserAuthenticationDto login(UserLoginDto userLoginDto);
}
