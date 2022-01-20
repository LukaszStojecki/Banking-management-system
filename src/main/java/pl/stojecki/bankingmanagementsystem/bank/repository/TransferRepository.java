package pl.stojecki.bankingmanagementsystem.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stojecki.bankingmanagementsystem.bank.model.Transfer;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
