package pl.stojecki.bankingmanagementsystem.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.user.repository.UserRepository;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        return userRepository.findByIdentificationNumber(number)
                .orElseThrow(() -> new UsernameNotFoundException("Identification number " + number + " not found"));
    }
}
