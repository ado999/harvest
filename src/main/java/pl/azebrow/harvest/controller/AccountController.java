package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.UserRequest;
import pl.azebrow.harvest.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
@Secured(RoleEnum.Constants.ADMIN)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createStaffAccount(
            @RequestBody UserRequest dto) {
        accountService.createStaffAccount(dto);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @PathVariable Long id,
            @RequestBody UserRequest dto
    ){
        accountService.updateAccount(id, dto);
        return ResponseEntity.ok(null);
    }

}
