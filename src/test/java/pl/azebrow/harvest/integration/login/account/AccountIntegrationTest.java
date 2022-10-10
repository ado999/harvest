package pl.azebrow.harvest.integration.login.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountIntegrationTest extends BaseIntegrationTest {

    private final static String LOGIN_URL = "/api/v1/account";

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void zisFirstStillInDb2() {
        assertEquals(0, accountRepository.findAll().stream().filter(a -> a.getFirstName().equals("First")).toList().size());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "STAFF")
    public void shouldCreateStaffAccount() throws Exception {
        var staffAccountRequest = AccountRequestBuilder.builder()
                .firstName("First")
                .lastName("Last")
                .email("email@admin.qp")
                .build();
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(staffAccountRequest)))
                .andExpect(status().isCreated());

        var staffAccount = accountRepository.findByEmail(staffAccountRequest.getEmail());
        assertTrue(staffAccount.isPresent());
        assertEquals(staffAccountRequest.getFirstName(), staffAccount.get().getFirstName());
        assertEquals(staffAccountRequest.getEmail(), staffAccount.get().getEmail());
        assertTrue(staffAccount.get().getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_STAFF")));
    }

    @Test
    public void isFirstStillInDb() {
        assertEquals(0, accountRepository.findAll().stream().filter(a -> a.getFirstName().equals("First")).toList().size());
    }

}
