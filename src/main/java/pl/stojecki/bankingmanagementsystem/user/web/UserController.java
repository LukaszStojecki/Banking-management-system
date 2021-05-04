package pl.stojecki.bankingmanagementsystem.user.web;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.user.dto.*;
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

    @PostMapping("/refreshToken")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws NotFoundException {
        return userService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) throws NotFoundException {
        userService.deleteByUserId(logOutRequest.getUserId());
        return new ResponseEntity<>("Log out successful!", HttpStatus.OK);
    }
}
