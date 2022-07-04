package pl.azebrow.harvest.service;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.User;
import pl.azebrow.harvest.repository.UserRepository;

@Service
public class HarvestUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public HarvestUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User \"%s\" not found", email))
                );
    }
}
