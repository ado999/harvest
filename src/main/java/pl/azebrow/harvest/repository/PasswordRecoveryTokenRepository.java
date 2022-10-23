package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.PasswordRecoveryToken;

import java.util.Collection;
import java.util.Optional;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, Long> {
    Optional<PasswordRecoveryToken> findByAccount(Account account);

    Optional<PasswordRecoveryToken> findByToken(String token);

    Collection<PasswordRecoveryToken> findAllByAccount(Account account);
}
