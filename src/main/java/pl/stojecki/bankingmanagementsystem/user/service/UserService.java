package pl.stojecki.bankingmanagementsystem.user.service;

import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.user.dto.RegisterRequest;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.model.VerificationToken;

public interface UserService extends UserDetailsService {

    void signup(RegisterRequest registerRequest) throws EmailException, NotFoundException, ConflictException;

    String generateVerificationToken(User user);

    void fetchUserAndEnable(VerificationToken verificationToken) throws NotFoundException;

    void verifyAccount(String token) throws NotFoundException;

}
