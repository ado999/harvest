package pl.azebrow.harvest.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.service.AccountStatusService;

@Service
@Profile("test")
@RequiredArgsConstructor
public class NullEmailService implements EmailService{

    private final AccountStatusService statusService;
    @Override
    public void sendRecoveryEmail(PasswordRecoveryToken recoveryToken, boolean newlyCreatedAccount) {
        var emailType = newlyCreatedAccount ? "account creation" : "password recovery";
        var token =recoveryToken.getToken();
        var account = recoveryToken.getAccount();
        var email = account.getEmail();
        System.out.printf("Sending %s token \"%s\" to address \"%s\"",
                emailType,
                token,
                email);
        statusService.setStatus(account, AccountStatus.CONFIRMATION_EMAIL_SENT);
    }
}
