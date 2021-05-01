package pl.stojecki.bankingmanagementsystem.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.user.dto.PasswordResetTokenRequest;
import pl.stojecki.bankingmanagementsystem.user.service.PasswordResetTokenService;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class ResetPasswordController {

    private final PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/resetPassword")
    public ResponseEntity<String> sendResetPasswordEmail(@RequestParam(name = "email") String email) throws NotFoundException, EmailException {
        passwordResetTokenService.sendResetPasswordEmail(email);
        return new ResponseEntity<>("Password reset email successfully sent", HttpStatus.OK);
    }

    @PutMapping("/resetPassword/{token}")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetTokenRequest passwordResetTokenRequest,
                                                @PathVariable(name = "token") String token) throws NotFoundException {
        passwordResetTokenService.resetPasswordToken(passwordResetTokenRequest, token);

        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }
}
