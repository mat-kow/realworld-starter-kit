package pl.teo.realworldapp.app;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.teo.realworldapp.app.jwt.JwtRequestFilter;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profiles/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/articles/*", "/api/articles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/articles/*/comments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/tags").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtRequestFilter(secretKey()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SecretKey secretKey() {
        String key = "longAndSecure longAndSecure longAndSecure longAndSecure longAndSecure longAndSecure";
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
