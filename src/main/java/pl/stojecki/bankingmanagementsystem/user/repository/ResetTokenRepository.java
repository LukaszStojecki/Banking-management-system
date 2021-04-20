package pl.stojecki.bankingmanagementsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stojecki.bankingmanagementsystem.user.model.ResetToken;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken,Long> {
}
