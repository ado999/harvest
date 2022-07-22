package pl.azebrow.harvest.service;

import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.exeption.InvalidTokenException;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.repository.PasswordRecoveryTokenRepository;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.request.PasswordChangeRequest;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final PasswordRecoveryTokenRepository tokenRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public void createPasswordRecoveryToken(String email, MailModel.Type type) {
        Optional<Account> optional = accountRepository.findByEmail(email);
        if (optional.isEmpty()) {
            return;
        }
        Account account = optional.get();
        PasswordRecoveryToken token = new PasswordRecoveryToken(account);
        tokenRepository.save(token);

        MailModel model = MailModel
                .builder()
                .name(account.getFirstName())
                .to(account.getEmail())
                .token(token.getToken())
                .mailType(type)
                .build();
        try {
            emailService.sendEmail(model);
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
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
        //todo password validation
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);
        tokenRepository.delete(token);
    }
}
