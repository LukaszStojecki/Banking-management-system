package pl.stojecki.bankingmanagementsystem.user.service;

import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.user.dto.RegisterRequest;
import pl.stojecki.bankingmanagementsystem.user.model.User;

public interface UserService extends UserDetailsService {

    void signup(RegisterRequest registerRequest) throws EmailException, NotFoundException, ConflictException;

    String generateVerificationToken(User user);

}
