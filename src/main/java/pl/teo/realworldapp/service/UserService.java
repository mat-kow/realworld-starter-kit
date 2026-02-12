package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.UserLoginDto;
import pl.teo.realworldapp.model.dto.UserRegisterDto;
import pl.teo.realworldapp.model.dto.UserUpdateDto;
import pl.teo.realworldapp.model.repositories.UserAuthenticationDto;

import java.security.Principal;

public interface UserService {
    UserAuthenticationDto register(UserRegisterDto registerDto);
    UserAuthenticationDto getCurrent(Principal principal);
    UserAuthenticationDto update(UserUpdateDto userUpdateDto, Principal principal);
    UserAuthenticationDto login(UserLoginDto userLoginDto);
}
