package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.exeption.InvalidTokenException;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.repository.PasswordRecoveryTokenRepository;
import pl.azebrow.harvest.request.PasswordChangeRequest;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private static final long EXPIRATION_MINUTES = 10;

    private final Clock clock;

    private final PasswordRecoveryTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;
    private AccountService accountService = null;

    public void initComponent(AccountService accountService) {
        this.accountService = accountService;
        emailService.initComponent(accountService);
    }

    public void createPasswordRecoveryToken(MailModel model) {
        var token = new PasswordRecoveryToken();
        var expiryDate = LocalDateTime.now(clock).plusMinutes(EXPIRATION_MINUTES);
        token.setAccount(model.getAccount());
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(expiryDate);
        tokenRepository.save(token);

        model.setToken(token.getToken());
        emailService.sendEmail(model);
    }

    public void createPasswordRecoveryToken(String email, MailModel.Type type) {
        var account = accountService.findAccountByEmail(email);
        var model = new MailModel(account, type);
        var oldTokens = tokenRepository.findAllByAccount(account);
        oldTokens.forEach(tokenRepository::delete);
        createPasswordRecoveryToken(model);
    }

    @Transactional(noRollbackFor = InvalidTokenException.class)
    public void recoverPassword(String tokenString, PasswordChangeRequest request) {
        PasswordRecoveryToken token = tokenRepository
                .findByToken(tokenString)
                .orElseThrow(
                        () -> new InvalidTokenException("Token not found!"));
        if (token.getExpiryDate() == null || token.getExpiryDate().isBefore(LocalDateTime.now(clock))) {
            tokenRepository.delete(token);
            throw new InvalidTokenException("Token expired");
        }
        Account account = token.getAccount();
        if (!account.getEmail().equals(request.getEmail())) {
            tokenRepository.delete(token);
            throw new InvalidTokenException("Email not valid!");
        }
        String password = passwordEncoder.encode(request.getPassword());
        accountService.setAccountPassword(account, password);
        accountService.setAccountStatus(account, AccountStatus.EMAIL_CONFIRMED);
        tokenRepository.delete(token);
    }
}
