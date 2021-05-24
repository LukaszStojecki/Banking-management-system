package pl.stojecki.bankingmanagementsystem.bank.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.bank.dto.PaymentRequest;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;
import pl.stojecki.bankingmanagementsystem.bank.model.Saldo;
import pl.stojecki.bankingmanagementsystem.bank.repository.BankAccountRepository;
import pl.stojecki.bankingmanagementsystem.bank.repository.SaldoRepository;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaldoService {

    private final SaldoRepository saldoRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public void createSaldo(Long accountId) throws NotFoundException {
        Saldo saldo = new Saldo();
        saldo.setValue(BigDecimal.valueOf(1000));
        saldo.setBankAccount(bankAccountRepository.findById(accountId).
                orElseThrow(() -> new NotFoundException("Account not found")));
        saldoRepository.save(saldo);
    }
    public Saldo getRecipientSaldo(BankAccount recipientSaldo) throws NotFoundException {
        return recipientSaldo.getSaldo().stream()
                .filter(saldo -> saldo.getBankAccount().equals(recipientSaldo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Recipient saldo not found"));
    }

    public Saldo getSourceSaldo(BankAccount sourceSaldo) throws NotFoundException {
        return sourceSaldo.getSaldo().stream()
                .filter(saldo -> saldo.getBankAccount().equals(sourceSaldo))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Source saldo not found"));
    }

}
