package pl.stojecki.bankingmanagementsystem.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String identificationNumber;
    private String password;
}
