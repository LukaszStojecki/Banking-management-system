package pl.stojecki.bankingmanagementsystem.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;


@Entity
@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private BigDecimal value;
    private String title;
    @ManyToOne
    private BankAccount recipientsBankAccount;
    @ManyToOne
    private BankAccount sourceBankAccount;
}
