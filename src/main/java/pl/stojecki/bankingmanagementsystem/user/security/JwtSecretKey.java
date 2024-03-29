package pl.stojecki.bankingmanagementsystem.user.security;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@AllArgsConstructor
@Configuration
public class JwtSecretKey {

    private final JwtUtils jwtUtils;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtUtils.getJwtSecret().getBytes());
    }

}
