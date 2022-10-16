package pl.azebrow.harvest.integration.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.response.AccountResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountIntegrationTest extends BaseIntegrationTest {

    private final static String ACCOUNT_URL = "/api/v1/account";

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldCreateStaffAccount() throws Exception {
        var staffAccountRequest = AccountRequestBuilder.builder()
                .firstName("First")
                .lastName("Last")
                .email("email@admin.qp")
                .accountRequest();
        mockMvc.perform(post(ACCOUNT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(staffAccountRequest)))
                .andExpect(status().isCreated());

        var staffAccount = accountRepository.findByEmail(staffAccountRequest.getEmail());
        assertTrue(staffAccount.isPresent());
        assertEquals(staffAccountRequest.getFirstName(), staffAccount.get().getFirstName());
        assertEquals(staffAccountRequest.getEmail(), staffAccount.get().getEmail());
        assertTrue(staffAccount.get().getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_STAFF")));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAccount() throws Exception {
        var result = mockMvc.perform(get(ACCOUNT_URL + "/1"))
                .andExpect(status().isOk())
                .andReturn();
        var content = result.getResponse().getContentAsString();
        var account = jsonUtils.parseObject(content, AccountResponse.class);

        assertEquals(1, account.getId());
        assertEquals("Tulley", account.getFirstName());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldUpdateAccount() throws Exception {
        var updateRequest = new AccountRequestBuilder()
                .firstName("First")
                .lastName("Last")
                .enabled(false)
                .accountUpdateRequest();
        mockMvc.perform(put(ACCOUNT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(updateRequest)))
                .andExpect(status().isNoContent());
        var account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals(updateRequest.getFirstName(), account.get().getFirstName());
        assertEquals(updateRequest.getLastName(), account.get().getLastName());
        assertEquals(updateRequest.getEnabled(), account.get().isEnabled());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldUpdateEmail() throws Exception {
        var emailUpdate = new AccountRequestBuilder()
                .email("email@admin.qp")
                .emailUpdateRequest();
        mockMvc.perform(put(ACCOUNT_URL + "/email/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(emailUpdate)))
                .andExpect(status().isNoContent());
        var account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals(emailUpdate.getEmail(), account.get().getEmail());
    }
}
