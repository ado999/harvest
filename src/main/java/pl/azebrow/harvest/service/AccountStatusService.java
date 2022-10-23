package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.repository.AccountRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountStatusService {

    private final AccountRepository accountRepository;

    public void setStatus(Account account, AccountStatus status) {
        account.setStatus(status);
        accountRepository.save(account);
    }

    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

}
