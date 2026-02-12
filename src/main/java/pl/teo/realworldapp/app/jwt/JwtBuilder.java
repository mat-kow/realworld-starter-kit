package pl.teo.realworldapp.app.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtBuilder {
    private final SecretKey secretKey;

    public String getToken(String subject) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        GrantedAuthority grantedAuthority = () -> "ROLE_USER";
        grantedAuthorities.add(grantedAuthority);
        Date now = new Date();
        return Jwts.builder()
                .subject(subject)
                .claim("authorities", grantedAuthorities)
                .issuedAt(now)
                .expiration(Date.from(now.toInstant().plus(14, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();
    }
}
