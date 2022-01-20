package pl.stojecki.bankingmanagementsystem.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccountType;
import pl.stojecki.bankingmanagementsystem.bank.model.Saldo;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccountOut {

    private Long id;
    private String number;
    private BankAccountType bankAccountType;
    private Set<SaldoOut> saldo;
}
