package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.model.Account;

import java.util.Optional;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, Long> {
    Optional<PasswordRecoveryToken> findByUser(Account account);

    Optional<PasswordRecoveryToken> findByToken(String token);
}
