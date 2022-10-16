package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.exeption.AccountNotFoundException;
import pl.azebrow.harvest.exeption.EmailAlreadyExistsException;
import pl.azebrow.harvest.exeption.RoleNotFoundException;
import pl.azebrow.harvest.mail.MailModel;
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
import pl.azebrow.harvest.utils.EmployeeCodeGenerator;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final EmployeeCodeGenerator codeGenerator;

    private final PasswordRecoveryService passwordRecoveryService;

    @PostConstruct
    public void initComponent() {
        passwordRecoveryService.initComponent(this);
    }

    public void createEmployee(EmployeeRequest request) {
        validateEmail(request.getEmail());
        Role accountRole = findRole(RoleEnum.USER);
        Account account = createAccount(request.toAccountRequest(), accountRole);
        Employee employee = Employee.builder()
                .code(codeGenerator.generateCode(request.getLastName()))
                .passportTaken(request.getPassportTaken())
                .phoneNumber(request.getPhoneNumber())
                .balance(BigDecimal.ZERO)
                .build();
        account.setEmployee(employee);
        accountRepository.saveAndFlush(account);
        MailModel model = new MailModel(account, MailModel.Type.PASSWORD_CREATION);
        passwordRecoveryService.createPasswordRecoveryToken(model);
    }

    public void createStaffAccount(AccountRequest dto) {
        validateEmail(dto.getEmail());
        Role staffRole = findRole(RoleEnum.STAFF);
        Account account = createAccount(dto, staffRole);
        accountRepository.saveAndFlush(account);
        MailModel model = new MailModel(account, MailModel.Type.PASSWORD_CREATION);
        passwordRecoveryService.createPasswordRecoveryToken(model);
    }

    public void updateAccount(Long id, AccountUpdateRequest updateRequest) {
        Account account = findAccountById(id);
        account.setFirstName(updateRequest.getFirstName());
        account.setLastName(updateRequest.getLastName());
        account.setEnabled(updateRequest.getEnabled());
        accountRepository.save(account);
    }

    public void updateAccountEmail(Long id, AccountEmailUpdateRequest updateRequest) {
        Account account = findAccountById(id);
        if (account.getEmail().equals(updateRequest.getEmail())) {
            return;
        }
        validateEmail(updateRequest.getEmail());
        account.setEmail(updateRequest.getEmail());
        account.setStatus(AccountStatus.EMAIL_CHANGED_NOT_CONFIRMED);
        account.setPassword("");
        accountRepository.saveAndFlush(account);
        MailModel model = new MailModel(account, MailModel.Type.PASSWORD_CREATION);
        passwordRecoveryService.createPasswordRecoveryToken(model);
    }

    public void setAccountPassword(Account account, String password) {
        account.setPassword(password);
        accountRepository.save(account);
    }

    public void setAccountStatus(Account account, AccountStatus status) {
        account.setStatus(status);
        accountRepository.save(account);
    }

    public void setAccountStatus(String email, AccountStatus status) {
        setAccountStatus(findAccountByEmail(email), status);
    }

    public Account findAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(String.format("User with id \"%d\" not found", id)));
    }

    public Account findAccountByEmail(String email) {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(String.format("User with email \"%s\" not found", email)));
    }

    private void validateEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(String.format("Email \"%s\" already exists!", email));
        }
    }

    private Role findRole(RoleEnum role) {
        return roleRepository
                .findByName(role.getName())
                .orElseThrow(
                        () -> new RoleNotFoundException(String.format("Role \"%s\" not found!", role))
                );
    }

    private Account createAccount(AccountRequest request, Role role) {
        return Account.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .roles(List.of(role))
                .enabled(true)
                .status(AccountStatus.ACCOUNT_CREATED)
                .build();
    }
}
