package pl.stojecki.bankingmanagementsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.stojecki.bankingmanagementsystem.user.model.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken,Long> {
}
