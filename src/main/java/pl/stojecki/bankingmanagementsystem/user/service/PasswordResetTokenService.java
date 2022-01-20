package pl.stojecki.bankingmanagementsystem.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stojecki.bankingmanagementsystem.exception.BadRequestException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.user.dto.NotificationEmail;
import pl.stojecki.bankingmanagementsystem.user.dto.PasswordResetTokenRequest;
import pl.stojecki.bankingmanagementsystem.user.model.PasswordResetToken;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.repository.PasswordResetTokenRepository;
import pl.stojecki.bankingmanagementsystem.user.repository.UserRepository;
import pl.stojecki.bankingmanagementsystem.user.security.JwtUtils;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    @Transactional
    public void sendResetPasswordEmail(String email) throws NotFoundException, EmailException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No user found for given email"));
        String passwordResetToken = generatePasswordResetToken(user);

        mailService.sendRegisterEmail(new NotificationEmail("Banking system password reset",
                user.getEmail(), "Please click the link below to reset your password " + "\n" + "\n" +
                "http://localhost:8080/api/auth/resetPassword/" + passwordResetToken
                + "\n" + "\n" + "We are informing you that the above link will expire 24 hours after being sent."));
    }

    private String generatePasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setCreatedDate(Instant.now().plusSeconds(jwtUtils.getPasswordResetExpirationSec()));
        passwordResetTokenRepository.save(passwordResetToken);
        return token;
    }

    @Transactional
    public void resetPasswordToken(PasswordResetTokenRequest passwordResetTokenRequest, String token) throws BadRequestException, NotFoundException {
        if (!passwordResetTokenRequest.getNewPassword().equals(passwordResetTokenRequest.getConfirmationPassword())) {
            throw new BadRequestException("New password is not the same as confirmation password");
        }
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token not found " + token));
        User user = passwordResetToken.getUser();
        if (passwordEncoder.matches(passwordResetTokenRequest.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password cannot be the same as the old password");
        }
        if (passwordResetToken.getCreatedDate().isAfter(Instant.now())) {
            user.setPassword(passwordEncoder.encode(passwordResetTokenRequest.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new BadRequestException("Token has expired");
        }
    }
}
