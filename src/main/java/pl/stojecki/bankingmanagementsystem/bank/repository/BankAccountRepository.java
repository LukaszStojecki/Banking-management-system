package pl.stojecki.bankingmanagementsystem.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;
import pl.stojecki.bankingmanagementsystem.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByNumber(String accountNumber);

    Optional<BankAccount> findByNumber(String number);

    List<BankAccount> findBankAccountByUser(User user);

    List<BankAccount> findByUserIdentificationNumber(String identifier);

}
