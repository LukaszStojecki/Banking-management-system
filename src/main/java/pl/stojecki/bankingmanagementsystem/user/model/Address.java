package pl.stojecki.bankingmanagementsystem.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String street;
    private String postCode;
    private String houseNumber;
    private String name;
    private String surname;
    private String phoneNumber;
    @OneToOne(mappedBy = "address")
    private User user;
    private LocalDate dateOfBirth;
}
