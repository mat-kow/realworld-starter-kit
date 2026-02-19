package pl.teo.realworldapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        if (userRepo.existsByEmailOrUsername(registerDto.getUsername(), registerDto.getEmail())) {
            //todo custom exception
            throw new RuntimeException();
        }
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User savedUser = userRepo.save(mapper.map(registerDto, User.class));
        return mapper.map(savedUser, UserAuthenticationDto.class);
    }

    @Override
    public UserAuthenticationDto getCurrentDto() {
        return mapper.map(getCurrentUser(), UserAuthenticationDto.class);
    }

    @Override
    public UserAuthenticationDto login(UserLoginDto userLoginDto) {
        User user = userRepo.findUserByEmailIgnoreCase(userLoginDto.getEmail())
                //todo custom exception
                .orElseThrow(() -> new RuntimeException("User does not exists"));
        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            UserAuthenticationDto authenticationDto = mapper.map(user, UserAuthenticationDto.class);
            authenticationDto.setToken(jwtBuilder.getToken(String.valueOf(user.getId())));
            return authenticationDto;
        }
        //todo return "Forbidden"
        return null;
    }

    @Override
    public Profile getProfileByUsername(String username) {
        User user = userRepo.findUserByUsername(username)
                //todo custom exception
                .orElseThrow(() -> new RuntimeException("User does not exists"));

        return mapper.map(user, Profile.class);
    }

    @Override
    public Profile followProfile(String profileName) {
        User currentUser = getCurrentUser();
        User toFollow = userRepo.findUserByUsername(profileName)
                //todo custom exception
                .orElseThrow(() -> new RuntimeException("User does not exists"));
        currentUser.getFollowed().add(toFollow);
        userRepo.save(currentUser);
        return mapper.map(toFollow, Profile.class);
    }

    @Override
    public Profile unfollowProfile(String profileName) {
        User currentUser = getCurrentUser();
        User toFollow = userRepo.findUserByUsername(profileName)
                //todo custom exception
                .orElseThrow(() -> new RuntimeException("User does not exists"));
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

        return mapper.map(
                userRepo.save(currentUser),
                UserAuthenticationDto.class);
    }

    @Override
    public User getCurrentUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findById(Long.parseLong(principal.getName()))
                //todo custom exception
                .orElseThrow(RuntimeException::new);
    }
}

