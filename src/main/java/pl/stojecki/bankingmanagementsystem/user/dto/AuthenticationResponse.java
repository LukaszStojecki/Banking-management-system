package pl.stojecki.bankingmanagementsystem.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticationResponse {

    private String authenticationToken;
    private String identificationNumber;

}
