package pl.stojecki.bankingmanagementsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.stojecki.bankingmanagementsystem.user.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
