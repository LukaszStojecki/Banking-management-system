package pl.stojecki.bankingmanagementsystem.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.stojecki.bankingmanagementsystem.user.model.User;

import javax.persistence.*;
import java.util.Set;


@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"saldo", "recipientTransfer", "sourceTransfer"})
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 26, max = 26)
    private String number;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Saldo> saldo;
    @Enumerated(value = EnumType.STRING)
    private BankAccountType bankAccountType;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(mappedBy = "recipientsBankAccount", cascade = CascadeType.ALL)
    private Set<Transfer> recipientTransfer;
    @OneToMany(mappedBy = "sourceBankAccount", cascade = CascadeType.ALL)
    private Set<Transfer> sourceTransfer;
}
