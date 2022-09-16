package pl.azebrow.harvest.utils;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.request.ValidatorTest;
import pl.azebrow.harvest.service.AccountBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CallerFacadeTest {

    CallerFacade facade;
    Account account;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        account = AccountBuilder.account().setId(123L).build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(account);
        SecurityContextHolder.setContext(securityContext);
        facade = new CallerFacade();
    }

    @Test
    void returnsAccount(){
        assertEquals(account, facade.getCaller());
    }
}