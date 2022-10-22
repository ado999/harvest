package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.request.PasswordChangeRequest;
import pl.azebrow.harvest.service.PasswordRecoveryService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recovery")
@Validated
public class PasswordRecoveryController {

    private final PasswordRecoveryService recoveryService;

    @PostMapping
    public void createPasswordRecoveryToken(
            @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") @RequestParam String email
    ) {
        recoveryService.createPasswordRecoveryToken(email, MailModel.Type.PASSWORD_RECOVERY);
    }

    @PostMapping("/{token}")
    public void recoverPassword(
            @NotBlank @PathVariable String token,
            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest
    ) {
        recoveryService.recoverPassword(token, passwordChangeRequest);
    }

}
