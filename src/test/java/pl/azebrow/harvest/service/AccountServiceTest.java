package pl.azebrow.harvest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.azebrow.harvest.exeption.EmailAlreadyExistsException;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.request.EmployeeRequest;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_not_create_new_account_when_email_exists() {
        var employeeRequest = new EmployeeRequest();
        employeeRequest.setEmail("any@email.net");

        when(accountRepository.existsByEmail(any(String.class)))
                .thenReturn(true);
        assertThrowsExactly(EmailAlreadyExistsException.class, () -> accountService.createEmployee(employeeRequest));
    }

//    @Test
//    void should(){
//        when(accountRepository.save(any())).
//    }
}