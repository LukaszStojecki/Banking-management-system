package pl.stojecki.bankingmanagementsystem.bank.service;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountRequest;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccountType;
import pl.stojecki.bankingmanagementsystem.bank.repository.BankAccountRepository;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.exception.UnauthorizedException;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.service.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;
    private final SaldoService saldoService;

    @Transactional
    public void createBankAccount(BankAccountRequest bankAccountRequest) throws UnauthorizedException, NotFoundException {
        BankAccount bankAccount = new BankAccount();
        User user = userService.getCurrentUser();
        String number = generateAccountNumber();
        bankAccount.setNumber(number);
        bankAccount.setUser(user);
        bankAccount.setBankAccountType(BankAccountType.valueOf(bankAccountRequest.getAccountType()));
        bankAccountRepository.save(bankAccount);
        saldoService.createSaldo(bankAccount.getId());
    }

    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = RandomStringUtils.randomNumeric(26);
        } while (accountNumber.charAt(0) == '0' || bankAccountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }
}
