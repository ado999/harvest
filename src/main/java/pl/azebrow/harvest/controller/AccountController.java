package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.AccountEmailUpdateRequest;
import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.request.AccountUpdateRequest;
import pl.azebrow.harvest.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
@Secured(RoleEnum.Constants.ADMIN)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createStaffAccount(
            @RequestBody AccountRequest dto) {
        accountService.createStaffAccount(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateAccount(
            @PathVariable Long id,
            @RequestBody AccountUpdateRequest updateRequest
    ) {
        accountService.updateAccount(id, updateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/email/{id}")
    public void updateAccountEmail(
            @PathVariable Long id,
            @RequestBody AccountEmailUpdateRequest updateRequest
    ) {
        accountService.updateAccountEmail(id, updateRequest);
    }

}
