package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.exeption.EmailAlreadyTakenException;
import pl.azebrow.harvest.exeption.RoleNotFoundException;
import pl.azebrow.harvest.exeption.UserNotFoundException;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Role;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.repository.RoleRepository;
import pl.azebrow.harvest.request.UserRequest;
import pl.azebrow.harvest.utils.EmployeeCodeGenerator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordRecoveryService passwordRecoveryService;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final EmployeeCodeGenerator codeGenerator;

    public void createEmployee(UserRequest dto) {
        validateEmail(dto.getEmail());
        Role accountRole = findRole(RoleEnum.USER);
        Account account = createUser(dto, accountRole);
        Employee employee = Employee.builder()
                .code(codeGenerator.generateCode(dto.getLastName()))
                .build();
        account.setEmployee(employee);
        accountRepository.saveAndFlush(account);
        passwordRecoveryService.createPasswordRecoveryToken(account.getEmail(), MailModel.Type.PASSWORD_CREATION);
    }

    public void createStaffAccount(UserRequest dto) {
        validateEmail(dto.getEmail());
        Role staffRole = findRole(RoleEnum.STAFF);
        Account account = createUser(dto, staffRole);
        accountRepository.saveAndFlush(account);
        passwordRecoveryService.createPasswordRecoveryToken(account.getEmail(), MailModel.Type.PASSWORD_CREATION);
    }

    public void updateAccount(Long id, UserRequest dto) {
        Account account = findUserById(id);
        account.setEmail(dto.getEmail());
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getFirstName());
    }

    public Account findUserById(Long id){
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id \"%d\" not found", id)));
    }

    private void validateEmail(String email){
        if (accountRepository.existsByEmail(email)) {
            throw new EmailAlreadyTakenException(String.format("Email \"%s\" already exists!", email));
        }
    }

    private Role findRole(RoleEnum role){
        return roleRepository
                .findByName(role.getName())
                .orElseThrow(
                        () -> new RoleNotFoundException(String.format("Role \"%s\" not found!", RoleEnum.USER))
                );
    }

    private Account createUser(UserRequest dto, Role role){
        return Account.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .roles(List.of(role))
                .build();
    }
}
