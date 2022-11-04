package pl.azebrow.harvest.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.service.AccountStatusService;

@Service
@Profile("test | no-mail")
@Slf4j
public class NullEmailService implements EmailService {

    private final AccountStatusService statusService;

    @Autowired
    public NullEmailService(AccountStatusService statusService) {
        this.statusService = statusService;
        log.warn("Using NullEmailService");
    }

    @Override
    public void sendRecoveryEmail(PasswordRecoveryToken recoveryToken, boolean newlyCreatedAccount) {
        var emailType = newlyCreatedAccount ? "account creation" : "password recovery";
        var token = recoveryToken.getToken();
        var account = recoveryToken.getAccount();
        var email = account.getEmail();
        log.debug("Sending {} token \"{}\" to address \"{}\"", emailType, token, email);
        statusService.setStatus(account, AccountStatus.CONFIRMATION_EMAIL_SENT);
    }
}
