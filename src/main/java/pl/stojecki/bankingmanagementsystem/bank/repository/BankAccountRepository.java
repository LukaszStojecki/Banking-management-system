package pl.stojecki.bankingmanagementsystem.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByNumber(String accountNumber);

}
