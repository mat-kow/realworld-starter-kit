package pl.teo.realworldapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.teo.realworldapp.app.jwt.JwtBuilder;
import pl.teo.realworldapp.model.User;
import pl.teo.realworldapp.model.dto.UserLoginDto;
import pl.teo.realworldapp.model.dto.UserRegisterDto;
import pl.teo.realworldapp.model.dto.UserUpdateDto;
import pl.teo.realworldapp.model.repositories.UserAuthenticationDto;
import pl.teo.realworldapp.model.repositories.UserRepo;

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
    public UserAuthenticationDto getCurrent() {
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
    public UserAuthenticationDto update(UserUpdateDto user) {
        User currentUser = getCurrentUser();

        if (user.getUsername() != null) currentUser.setUsername(user.getUsername());
        if (user.getEmail() != null) currentUser.setEmail(user.getEmail());
        //todo encrypt
        if (user.getPassword() != null) currentUser.setPassword(user.getPassword());
        if (user.getBio() != null) currentUser.setBio(user.getBio());
        if (user.getImage() != null) currentUser.setImage(user.getImage());

        return mapper.map(
                userRepo.save(currentUser),
                UserAuthenticationDto.class);
    }

    private User getCurrentUser() {
        //todo get it from JWT
        return new User("qw","qw",null);
    }
}

