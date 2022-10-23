package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.repository.AccountRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    public void setPassword(Account account, String password) {
        var encodedPassword = passwordEncoder.encode(password);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    public void resetPassword(Account account) {
        account.setPassword(null);
        accountRepository.save(account);
    }
}
