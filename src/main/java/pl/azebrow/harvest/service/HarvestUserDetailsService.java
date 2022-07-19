package pl.azebrow.harvest.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.repository.AccountRepository;

@Service
public class HarvestUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public HarvestUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User \"%s\" not found", email))
                );
    }
}
