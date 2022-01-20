package pl.stojecki.bankingmanagementsystem.bank.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.bank.dto.TransferRequest;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;
import pl.stojecki.bankingmanagementsystem.bank.model.Saldo;
import pl.stojecki.bankingmanagementsystem.bank.model.Transfer;
import pl.stojecki.bankingmanagementsystem.bank.repository.BankAccountRepository;
import pl.stojecki.bankingmanagementsystem.bank.repository.TransferRepository;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@AllArgsConstructor
public class TransferService {


    private BankAccountRepository bankAccountRepository;
    private TransferRepository transferRepository;
    private SaldoService saldoService;

    @Transactional
    public void transferToAccount(TransferRequest transferRequest) throws NotFoundException, ConflictException {
        Transfer transfer = new Transfer();
        BankAccount source = bankAccountRepository.findByNumber(transferRequest.getSourceBankAccount())
                .orElseThrow(() -> new NotFoundException("source account by number " + transferRequest.getSourceBankAccount() + " not found"));
        BankAccount target = bankAccountRepository.findByNumber(transferRequest.getTargetAccount())
                .orElseThrow(() -> new NotFoundException("target account by number " + transferRequest.getTargetAccount() + " not found"));

        Saldo sourceSaldo = saldoService.getSourceSaldo(source);
        Saldo targetSaldo = saldoService.getRecipientSaldo(target);

        if (sourceSaldo.getValue().compareTo(transferRequest.getValue()) < 0) {
            throw new ConflictException("Not enough balance to finalize transfer");
        }
        sourceSaldo.setValue(sourceSaldo.getValue().subtract(transferRequest.getValue()));
        targetSaldo.setValue(targetSaldo.getValue().add(transferRequest.getValue()));
        transfer.setSourceBankAccount(source);
        transfer.setRecipientsBankAccount(target);
        transfer.setTitle(transferRequest.getTitle());
        transfer.setDate(Instant.now());
        transfer.setValue(transferRequest.getValue());
        transferRepository.save(transfer);
    }
}
