package pl.stojecki.bankingmanagementsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stojecki.bankingmanagementsystem.user.model.Address;
import pl.stojecki.bankingmanagementsystem.user.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    Address findByUser(User user);

}
