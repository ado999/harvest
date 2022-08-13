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

    public void createPasswordRecoveryToken(Account account, MailModel.Type type) {
        PasswordRecoveryToken token = new PasswordRecoveryToken(account);
        tokenRepository.save(token);

        MailModel model = MailModel
                .builder()
                .name(account.getFirstName())
                .to(account.getEmail())
                .token(token.getToken())
                .mailType(type)
                .build();
        emailService.sendEmail(model);
    }

    public void createPasswordRecoveryToken(String email, MailModel.Type type) {
        Account account = accountService.getAccountByEmail(email);
        createPasswordRecoveryToken(account, type);
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
