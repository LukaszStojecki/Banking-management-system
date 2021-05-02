package pl.stojecki.bankingmanagementsystem.user.web;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.user.dto.AuthenticationResponse;
import pl.stojecki.bankingmanagementsystem.user.dto.LoginRequest;
import pl.stojecki.bankingmanagementsystem.user.dto.RegisterRequest;
import pl.stojecki.bankingmanagementsystem.user.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest) throws EmailException, ConflictException {
        userService.signup(registerRequest);
        return new ResponseEntity<>("User Registration successful", HttpStatus.OK);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws NotFoundException {
        userService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/remindIdentificationNumber")
    public ResponseEntity<String> remindIdentificationNumber(@RequestParam(name = "email") String email) throws EmailException, NotFoundException {
        userService.remindIdentificationNumber(email);
        return new ResponseEntity<>("Identification number reminder email successfully sent", HttpStatus.OK);
    }

}
