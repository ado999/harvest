package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.enums.RoleEnum;
import pl.azebrow.harvest.exeption.AccountNotFoundException;
import pl.azebrow.harvest.exeption.EmailAlreadyExistsException;
import pl.azebrow.harvest.exeption.RoleNotFoundException;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Role;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.repository.RoleRepository;
import pl.azebrow.harvest.request.AccountEmailUpdateRequest;
import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.request.AccountUpdateRequest;
import pl.azebrow.harvest.request.EmployeeRequest;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountStatusService accountStatusService;
    private final PasswordService passwordService;
    private final EmployeeService employeeService;
    private final PasswordRecoveryService passwordRecoveryService;

    public void createEmployee(EmployeeRequest request) {
        var accountRole = findRole(RoleEnum.USER);
        var employee = employeeService.createEmployee(request);
        createAccount(request.toAccountRequest(), accountRole, employee);
    }

    public void createStaffAccount(AccountRequest request) {
        var staffRole = findRole(RoleEnum.STAFF);
        createAccount(request, staffRole, null);
    }

    public void updateAccount(Long id, AccountUpdateRequest updateRequest) {
        Account account = findAccountById(id);
        account.setFirstName(updateRequest.getFirstName());
        account.setLastName(updateRequest.getLastName());
        account.setEnabled(updateRequest.getEnabled());
        accountRepository.save(account);
    }

    public void updateAccountEmail(Long id, AccountEmailUpdateRequest updateRequest) {
        var email = updateRequest.getEmail();
        Account account = findAccountById(id);
        if (account.getEmail().equals(email)) {
            return;
        }
        validateEmail(email);
        account.setEmail(email);
        accountRepository.saveAndFlush(account);
        passwordService.resetPassword(account);
        accountStatusService.setStatus(account, AccountStatus.EMAIL_CHANGED_NOT_CONFIRMED);
        passwordRecoveryService.createPasswordRecoveryToken(account, false);
    }

    public Account findAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(String.format("User with id \"%d\" not found", id)));
    }

    private void validateEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(String.format("Email \"%s\" already exists!", email));
        }
    }

    Role findRole(RoleEnum role) {
        return roleRepository
                .findByName(role.getName())
                .orElseThrow(
                        () -> new RoleNotFoundException(String.format("Role \"%s\" not found!", role))
                );
    }

    private void createAccount(AccountRequest request, Role role, Employee employee) {
        validateEmail(request.getEmail());
        var account = Account.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .employee(employee)
                .roles(List.of(role))
                .status(AccountStatus.ACCOUNT_CREATED)
                .enabled(true)
                .build();
        accountRepository.saveAndFlush(account);
        passwordRecoveryService.createPasswordRecoveryToken(account, true);
    }
}
