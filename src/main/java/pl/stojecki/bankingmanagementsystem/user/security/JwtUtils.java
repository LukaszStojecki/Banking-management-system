package pl.stojecki.bankingmanagementsystem.user.security;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtUtils {

    @Value("${jwt.jwtSecret}")
    private String jwtSecret;
    @Value("${jwt.jwtExpirationSec}")
    private Integer jwtExpirationSec;
    @Value("${jwt.jwtRefreshExpirationSec}")
    private Long refreshTokenDurationSec;
    @Value("${jwt.passwordResetExpirationSec}")
    private Long passwordResetExpirationSec;

}
