package pl.teo.realworldapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.teo.realworldapp.app.exception.ApiNonUniqueValueException;
import pl.teo.realworldapp.app.exception.ApiNotFoundException;
import pl.teo.realworldapp.app.exception.ApiUnAuthorizedException;
import pl.teo.realworldapp.app.jwt.JwtBuilder;
import pl.teo.realworldapp.model.entity.User;
import pl.teo.realworldapp.model.dto.*;
import pl.teo.realworldapp.model.repositories.UserRepo;
import pl.teo.realworldapp.service.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceDefault implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtBuilder jwtBuilder;

    @Override
    public UserAuthenticationDto register(UserRegisterDto registerDto) {
        if (userRepo.existsByEmailOrUsernameIgnoreCase(registerDto.getEmail(), registerDto.getUsername())) {
            throw new ApiNonUniqueValueException("duplicated username or email");
        }
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User savedUser = userRepo.save(mapper.map(registerDto, User.class));
        return getUserAuthenticationDto(savedUser);
    }

    @Override
    public UserAuthenticationDto getCurrentDto() {
        return getUserAuthenticationDto(getCurrentUser());
    }

    @Override
    public UserAuthenticationDto login(UserLoginDto userLoginDto) {
        User user = userRepo.findUserByEmailIgnoreCase(userLoginDto.getEmail())
                .orElseThrow(() -> new ApiNotFoundException("User does not exists"));
        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return getUserAuthenticationDto(user);
        }
        throw  new ApiUnAuthorizedException("wrong password");
    }

    @Override
    public Profile getProfileByUsername(String username) {
        User user = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new ApiNotFoundException("User does not exists"));

        return mapper.map(user, Profile.class);
    }

    @Override
    public Profile followProfile(String profileName) {
        User currentUser = getCurrentUser();
        User toFollow = userRepo.findUserByUsername(profileName)
                .orElseThrow(() -> new ApiNotFoundException("User does not exists"));
        currentUser.getFollowed().add(toFollow);
        userRepo.save(currentUser);
        return mapper.map(toFollow, Profile.class);
    }

    @Override
    public Profile unfollowProfile(String profileName) {
        User currentUser = getCurrentUser();
        User toFollow = userRepo.findUserByUsername(profileName)
                .orElseThrow(() -> new ApiNotFoundException("User does not exists"));
        currentUser.getFollowed().remove(toFollow);
        userRepo.save(currentUser);
        return mapper.map(toFollow, Profile.class);
    }

    @Override
    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    public UserAuthenticationDto update(UserUpdateDto user) {
        User currentUser = getCurrentUser();

        if (user.getUsername() != null) currentUser.setUsername(user.getUsername());
        if (user.getEmail() != null) currentUser.setEmail(user.getEmail());
        if (user.getPassword() != null) currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getBio() != null) currentUser.setBio(user.getBio());
        if (user.getImage() != null) currentUser.setImage(user.getImage());

        return getUserAuthenticationDto(userRepo.save(currentUser));
    }

    @Override
    public User getCurrentUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findById(Long.parseLong(principal.getName()))
                .orElseThrow(() -> new ApiNotFoundException("User doesn't exists!"));
    }

    private UserAuthenticationDto getUserAuthenticationDto(User user) {
        UserAuthenticationDto dto = mapper.map(user, UserAuthenticationDto.class);
        dto.setToken(jwtBuilder.getToken(String.valueOf(user.getId())));
        return dto;
    }
}

