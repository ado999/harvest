package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.request.PasswordChangeRequest;
import pl.azebrow.harvest.service.PasswordRecoveryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recovery")
public class PasswordRecoveryController {

    private final PasswordRecoveryService recoveryService;

    @PostMapping
    public ResponseEntity<?> createPasswordRecoveryToken(
            @RequestParam String email
    ) {
        recoveryService.createPasswordRecoveryToken(email);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{token}")
    public ResponseEntity<?> recoverPassword(
            @PathVariable String token,
            @RequestBody PasswordChangeRequest passwordChangeRequest
    ) {
        recoveryService.recoverPassword(token, passwordChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
