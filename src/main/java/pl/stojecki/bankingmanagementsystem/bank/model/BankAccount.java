package pl.stojecki.bankingmanagementsystem.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.stojecki.bankingmanagementsystem.user.model.User;

import javax.persistence.*;
import java.util.Set;


@Entity
@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 26,max = 26)
    private String number;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private Set<Saldo> saldo;
    @Enumerated(value = EnumType.STRING)
    private BankAccountType bankAccountType;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
