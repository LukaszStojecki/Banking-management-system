package pl.stojecki.bankingmanagementsystem.bank.service;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountOut;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountRequest;
import pl.stojecki.bankingmanagementsystem.bank.mappers.BankAccountMapper;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccountType;
import pl.stojecki.bankingmanagementsystem.bank.repository.BankAccountRepository;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.exception.UnauthorizedException;
import pl.stojecki.bankingmanagementsystem.user.model.User;
import pl.stojecki.bankingmanagementsystem.user.service.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;
    private final SaldoService saldoService;
    private final BankAccountMapper bankAccountMapper;

    @Transactional
    public BankAccountOut createBankAccount(BankAccountRequest bankAccountRequest) throws UnauthorizedException, NotFoundException {
        BankAccount bankAccount = new BankAccount();
        User user = userService.getCurrentUser();
        String number = generateAccountNumber();
        bankAccount.setNumber(number);
        bankAccount.setUser(user);
        bankAccount.setBankAccountType(BankAccountType.valueOf((bankAccountRequest.getBankAccountType())));
        bankAccountRepository.save(bankAccount);
        saldoService.createSaldo(bankAccount.getId());
        return bankAccountMapper.entityToDTO(bankAccount);

    }

    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = RandomStringUtils.randomNumeric(26);
        } while (accountNumber.charAt(0) == '0' || bankAccountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }


    public BankAccountOut findById(Long id) {
        return bankAccountRepository.findById(id)
                .map(bankAccountMapper::entityToDTO)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public List<BankAccountOut> findByUser() {
        return bankAccountRepository.findByUserIdentificationNumber(SecurityContextHolder.getContext().getAuthentication().getName())
                .stream()
                .map(bankAccountMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public List<BankAccountType> findAllBankAccountType() {
        return Arrays.asList(BankAccountType.values());

    }


}
