package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.UserLoginDto;
import pl.teo.realworldapp.model.dto.UserRegisterDto;
import pl.teo.realworldapp.model.dto.UserUpdateDto;
import pl.teo.realworldapp.model.repositories.UserAuthenticationDto;

public interface UserService {
    UserAuthenticationDto register(UserRegisterDto registerDto);
    UserAuthenticationDto getCurrent();
    UserAuthenticationDto update(UserUpdateDto userUpdateDto);
    UserAuthenticationDto login(UserLoginDto userLoginDto);
}
