package pl.azebrow.harvest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.request.AccountEmailUpdateRequest;
import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.request.AccountUpdateRequest;
import pl.azebrow.harvest.response.AccountResponse;
import pl.azebrow.harvest.service.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/account")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public AccountResponse getAccount(@NotNull @Min(1) @PathVariable Long id) {
        var account = accountService.findAccountById(id);
        return mapper.map(account, AccountResponse.class);
    }

    @Operation(summary = "Create staff account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "409", description = "Email is being used")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createStaffAccount(
            @Valid @RequestBody AccountRequest request) {
        accountService.createStaffAccount(request);
    }

    @Operation(summary = "Update first name, last name and disable/enable account")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "404", description = "No account found with the given id")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateAccount(
            @NotNull @Min(1) @PathVariable Long id,
            @Valid @RequestBody AccountUpdateRequest updateRequest
    ) {
        accountService.updateAccount(id, updateRequest);
    }


    @Operation(summary = "Update account email", description = "Updates email and sends validation email")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update successful"),
            @ApiResponse(responseCode = "409", description = "Email is being used")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/email/{id}")
    public void updateAccountEmail(
            @NotNull @Min(1) @PathVariable Long id,
            @Valid @RequestBody AccountEmailUpdateRequest updateRequest
    ) {
        accountService.updateAccountEmail(id, updateRequest);
    }

}
