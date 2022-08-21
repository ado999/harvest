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

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final PasswordRecoveryTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;
    private AccountService accountService = null;

    public void initComponent(AccountService accountService) {
        this.accountService = accountService;
        emailService.initComponent(accountService);
    }

    public void createPasswordRecoveryToken(MailModel model) {
        PasswordRecoveryToken token = new PasswordRecoveryToken(model.getAccount());
        tokenRepository.save(token);

        model.setToken(token.getToken());
        emailService.sendEmail(model);
    }

    public void createPasswordRecoveryToken(String email, MailModel.Type type) {
        Account account = accountService.getAccountByEmail(email);
        MailModel model = new MailModel(account, type);
        createPasswordRecoveryToken(model);
    }

    public void recoverPassword(String tokenString, PasswordChangeRequest request) {
        PasswordRecoveryToken token = tokenRepository
                .findByToken(tokenString)
                .orElseThrow(
                        () -> new InvalidTokenException("Token not found!"));
        if (token.getExpiryDate() == null || token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token expired");
        }
        Account account = token.getAccount();
        if (!account.getEmail().equals(request.getEmail())) {
            throw new InvalidTokenException("Email not valid!");
        }
        String password = passwordEncoder.encode(request.getPassword());
        accountService.setAccountPassword(account, password);
        accountService.setAccountStatus(account, AccountStatus.EMAIL_CONFIRMED);
        tokenRepository.delete(token);
    }
}
