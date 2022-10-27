package pl.azebrow.harvest.service.email;

import org.springframework.scheduling.annotation.Async;
import pl.azebrow.harvest.model.PasswordRecoveryToken;

public interface EmailService {
    @Async
    void sendRecoveryEmail(PasswordRecoveryToken recoveryToken, boolean newlyCreatedAccount);
}
