package pl.stojecki.bankingmanagementsystem.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.exception.TokenRefreshException;
import pl.stojecki.bankingmanagementsystem.user.model.RefreshToken;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.repository.RefreshTokenRepository;
import pl.stojecki.bankingmanagementsystem.user.security.JwtUtils;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken generateRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(jwtUtils.getRefreshTokenDurationSec()));
        refreshToken.setUser(user);
        refreshToken.setRefreshCount(0L);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public void increaseCount(RefreshToken refreshToken) {
        verifyExpiration(refreshToken);
        refreshToken.incrementRefreshCount();
        refreshTokenRepository.save(refreshToken);
    }
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
    }
    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
