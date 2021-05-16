package pl.stojecki.bankingmanagementsystem.bank.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.bank.model.Saldo;
import pl.stojecki.bankingmanagementsystem.bank.repository.BankAccountRepository;
import pl.stojecki.bankingmanagementsystem.bank.repository.SaldoRepository;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaldoService {

    private final SaldoRepository saldoRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public void createSaldo(Long accountId) throws NotFoundException {
        Saldo saldo = new Saldo();
        saldo.setValue(BigDecimal.ZERO);
        saldo.setBankAccount(bankAccountRepository.findById(accountId).
                orElseThrow(() -> new NotFoundException("Account not found")));
        saldoRepository.save(saldo);
    }
}
