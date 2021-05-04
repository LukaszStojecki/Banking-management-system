package pl.stojecki.bankingmanagementsystem.user.security;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.stojecki.bankingmanagementsystem.user.model.Role;
import pl.stojecki.bankingmanagementsystem.user.model.User;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private final SecretKey secretKey;
    private final JwtUtils jwtUtils;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getIdentificationNumber())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtUtils.getJwtExpirationSec())))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String generateTokenFromUserIdentificationAndRole(String identificationNumber, Role role) {
        return Jwts.builder()
                .setSubject(identificationNumber)
                .claim("authorities", Set.of(new SimpleGrantedAuthority(role.toString())))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtUtils.getJwtExpirationSec())))
                .signWith(secretKey)
                .compact();
    }

}
