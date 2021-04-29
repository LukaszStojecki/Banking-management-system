package pl.stojecki.bankingmanagementsystem.user.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stojecki.bankingmanagementsystem.exception.BadRequestException;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.user.dto.AuthenticationResponse;
import pl.stojecki.bankingmanagementsystem.user.dto.LoginRequest;
import pl.stojecki.bankingmanagementsystem.user.dto.NotificationEmail;
import pl.stojecki.bankingmanagementsystem.user.dto.RegisterRequest;
import pl.stojecki.bankingmanagementsystem.user.model.Address;
import pl.stojecki.bankingmanagementsystem.user.model.Role;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.model.VerificationToken;
import pl.stojecki.bankingmanagementsystem.user.repository.AddressRepository;
import pl.stojecki.bankingmanagementsystem.user.repository.UserRepository;
import pl.stojecki.bankingmanagementsystem.user.repository.VerificationTokenRepository;
import pl.stojecki.bankingmanagementsystem.user.security.JwtProvider;
import pl.stojecki.bankingmanagementsystem.user.utils.PhoneNumberValidator;
import pl.stojecki.bankingmanagementsystem.user.utils.PostCodeValidator;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signup(RegisterRequest registerRequest) throws EmailException, ConflictException {

        registerValidator(registerRequest);

        User user = createUser(registerRequest);
        Address address = createAddress(registerRequest);
        user.setAddress(address);

        String token = generateVerificationToken(user);

        String identifier = user.getIdentificationNumber();

        mailService.sendRegisterEmail(new NotificationEmail("Please activate your account", ""
                + user.getEmail(), "Please click the link below to activate your account " + "\n" + "\n" +
                "http:localhost:8080/auth/accountVerification/" + token +  "\n" + "\n" +" Your identification number is: " + identifier));
    }


    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }


    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) throws NotFoundException {
        String identificationNumber = verificationToken.getUser().getUsername();
        User user = userRepository.findByIdentificationNumber(identificationNumber).orElseThrow(() -> new NotFoundException("User not found " + identificationNumber));
        user.setEnabled(true);
        user.setCredentials(true);
        user.setLocked(true);
        user.setExpired(true);
        userRepository.save(user);
    }


    public void verifyAccount(String token) throws NotFoundException {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new BadRequestException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private String generateIdentifier() {
        String identifier;
        do {
            identifier = RandomStringUtils.randomNumeric(8);
        } while (identifier.charAt(0) == '0' || userRepository.existsByIdentificationNumber(identifier));

        return identifier;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getIdentificationNumber(), loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .identificationNumber(loginRequest.getIdentificationNumber())
                .build();
    }

    public boolean registerValidator(RegisterRequest registerRequest) throws ConflictException {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        if (userRepository.existsByAddress_PhoneNumber(registerRequest.getPhoneNumber())) {
            throw new ConflictException("Phone number already exists");
        }
        if (!PostCodeValidator.PostCodeValidator(registerRequest.getPostCode())) {
            throw new BadRequestException("Post code needs to have dd-ddd format");
        }
        if (!PhoneNumberValidator.isValid(registerRequest.getPhoneNumber())) {
            throw new BadRequestException("Phone number needs to have ddd-ddd-ddd format");
        }
        return true;
    }

    public User createUser(RegisterRequest registerRequest) {

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setLocked(false);
        user.setEnabled(false);
        user.setCredentials(false);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Role.USER);
        String identifier = generateIdentifier();
        user.setIdentificationNumber(identifier);
        userRepository.save(user);


        return user;
    }

    public Address createAddress(RegisterRequest registerRequest){
        Address address = new Address();
        address.setCity(registerRequest.getCity());
        address.setHouseNumber(registerRequest.getHouseNumber());
        address.setName(registerRequest.getFirstName());
        address.setSurname(registerRequest.getLastName());
        address.setStreet(registerRequest.getStreet());
        address.setPhoneNumber(registerRequest.getPhoneNumber());
        address.setPostCode(registerRequest.getPostCode());
        address.setDateOfBirth(registerRequest.getDateOfBirth());
        addressRepository.save(address);
        return address;
    }
}
