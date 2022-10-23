package pl.azebrow.harvest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import pl.azebrow.harvest.enums.RoleEnum;
import pl.azebrow.harvest.exeption.EmailAlreadyExistsException;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.Role;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.repository.RoleRepository;
import pl.azebrow.harvest.request.EmployeeRequest;
import pl.azebrow.harvest.utils.EmployeeCodeGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordRecoveryService passwordRecoveryService;

    @Mock
    EmployeeCodeGenerator codeGenerator;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    AccountService accountService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_throw_when_email_exists() {
        var employeeRequest = new EmployeeRequest();
        employeeRequest.setEmail("any@email.net");

        when(accountRepository.existsByEmail(any())).thenReturn(true);
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(new Role()));
        assertThrows(EmailAlreadyExistsException.class, () -> accountService.createEmployee(employeeRequest));
    }

    @Test
    void should_successfully_create_employee() {
        var request = new EmployeeRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setEmail("e@ma.il");
        request.setPhoneNumber("123456789");
        request.setPassportTaken(false);

        when(accountRepository.existsByEmail(any())).thenReturn(false);
        when(roleRepository.findByName(any())).thenReturn(Optional.of(new Role(1L, "ROLE_USER")));
        when(codeGenerator.generateCode(any())).thenReturn("ABC12345");
        when(accountRepository.save(any(Account.class))).then(invocation -> {
            var account = (Account) invocation.getArgument(0);
            ReflectionTestUtils.setField(account, "id", 123L);
            return account;
        });

        accountService.createEmployee(request);

        verify(accountRepository).existsByEmail(request.getEmail());
        verify(roleRepository).findByName(RoleEnum.USER.getName());
    }

}