package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.request.PasswordChangeRequest;
import pl.azebrow.harvest.service.PasswordRecoveryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recovery")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
public class PasswordRecoveryController {

    private final PasswordRecoveryService recoveryService;

    @PostMapping
    public void createPasswordRecoveryToken(
            @RequestParam String email
    ) {
        recoveryService.createPasswordRecoveryToken(email, MailModel.Type.PASSWORD_RECOVERY);
    }

    @PostMapping("/{token}")
    public void recoverPassword(
            @PathVariable String token,
            @RequestBody PasswordChangeRequest passwordChangeRequest
    ) {
        recoveryService.recoverPassword(token, passwordChangeRequest);
    }

}
