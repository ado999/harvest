package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.exeption.InvalidTokenException;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.repository.PasswordRecoveryTokenRepository;
import pl.azebrow.harvest.request.PasswordChangeRequest;
import pl.azebrow.harvest.service.email.EmailService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import static pl.azebrow.harvest.model.AccountStatus.EMAIL_CONFIRMED;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private static final long EXPIRATION_MINUTES = 10;
    private final Clock clock;
    private final PasswordRecoveryTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordService passwordService;
    private final AccountStatusService accountStatusService;

    public void createPasswordRecoveryToken(String email) {
        var account = accountStatusService.findAccountByEmail(email);
        account.ifPresent(a -> createPasswordRecoveryToken(a, false));
    }

    public void createPasswordRecoveryToken(Account account, boolean newlyCreatedAccount) {
        removeOldTokens(account);
        var token = new PasswordRecoveryToken();
        var expiryDate = LocalDateTime.now(clock).plusMinutes(EXPIRATION_MINUTES);
        token.setAccount(account);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(expiryDate);
        tokenRepository.save(token);
        emailService.sendRecoveryEmail(token, newlyCreatedAccount);
    }

    @Transactional(noRollbackFor = InvalidTokenException.class)
    public void recoverPassword(String tokenString, PasswordChangeRequest request) {
        var token = tokenRepository
                .findByToken(tokenString)
                .orElseThrow(
                        () -> new InvalidTokenException("Token not found!"));
        if (token.getExpiryDate() == null || token.getExpiryDate().isBefore(LocalDateTime.now(clock))) {
            tokenRepository.delete(token);
            throw new InvalidTokenException("Token expired");
        }
        var account = token.getAccount();
        passwordService.setPassword(account, request.getPassword());
        accountStatusService.setStatus(account, EMAIL_CONFIRMED);
        tokenRepository.delete(token);
    }

    private void removeOldTokens(Account account) {
        var oldTokens = tokenRepository.findAllByAccount(account);
        oldTokens.forEach(tokenRepository::delete);
    }
}
