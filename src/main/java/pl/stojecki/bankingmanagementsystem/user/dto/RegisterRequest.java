package pl.stojecki.bankingmanagementsystem.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String city;
    private String street;
    private String postCode;
    private String houseNumber;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
