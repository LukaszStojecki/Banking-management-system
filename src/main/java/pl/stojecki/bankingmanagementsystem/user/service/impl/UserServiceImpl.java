package pl.stojecki.bankingmanagementsystem.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stojecki.bankingmanagementsystem.exception.BadRequestException;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.user.dto.NotificationEmail;
import pl.stojecki.bankingmanagementsystem.user.dto.RegisterRequest;
import pl.stojecki.bankingmanagementsystem.user.model.Address;
import pl.stojecki.bankingmanagementsystem.user.model.Role;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.model.VerificationToken;
import pl.stojecki.bankingmanagementsystem.user.repository.AddressRepository;
import pl.stojecki.bankingmanagementsystem.user.repository.UserRepository;
import pl.stojecki.bankingmanagementsystem.user.repository.VerificationTokenRepository;
import pl.stojecki.bankingmanagementsystem.user.service.UserService;
import pl.stojecki.bankingmanagementsystem.user.utils.PhoneNumberValidator;
import pl.stojecki.bankingmanagementsystem.user.utils.PostCodeValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailServiceImpl mailService;
    private final AddressRepository addressRepository;

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        return userRepository.findByIdentificationNumber(number)
                .orElseThrow(() -> new UsernameNotFoundException("Identification number " + number + " not found"));
    }
    @Override
    @Transactional
    public void signup(RegisterRequest registerRequest) throws EmailException, ConflictException {

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ConflictException("Email already exists");
        }
        if (userRepository.existsByAddress_PhoneNumber(registerRequest.getPhoneNumber())){
            throw new ConflictException("Phone number already exists");
        }
        if (!PostCodeValidator.PostCodeValidator(registerRequest.getPostCode())){
            throw new BadRequestException("Post code needs to have dd-ddd format");
        }
        if (!PhoneNumberValidator.isValid(registerRequest.getPhoneNumber())){
            throw new BadRequestException("Phone number needs to have ddd-ddd-ddd format");
        }
        User user = new User();
        Address address = new Address();
        address.setCity(registerRequest.getCity());
        address.setHouseNumber(registerRequest.getHouseNumber());
        address.setName(registerRequest.getFirstName());
        address.setSurname(registerRequest.getLastName());
        address.setStreet(registerRequest.getStreet());
        address.setPhoneNumber(registerRequest.getPhoneNumber());
        address.setPostCode(registerRequest.getPostCode());
        address.setDateOfBirth(registerRequest.getDateOfBirth());
        user.setAddress(address);
        user.setEmail(registerRequest.getEmail());
        user.setLocked(false);
        user.setEnabled(false);
        user.setCredentials(false);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Role.USER);
        String identifier = generateIdentifier();
        user.setIdentificationNumber(identifier);
        userRepository.save(user);
        addressRepository.save(address);

        String token = generateVerificationToken(user);

        mailService.sendRegisterEmail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Please click the link below to activate your account " +
                "http://localhost:8080/api/auth/" + token + "Your identification number is:" + identifier));
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String generateIdentifier() {
        String identifier;
        do {
            identifier = RandomStringUtils.randomNumeric(8);
        } while (identifier.charAt(0) == '0' || userRepository.existsByIdentificationNumber(identifier));

        return identifier;
    }

}
