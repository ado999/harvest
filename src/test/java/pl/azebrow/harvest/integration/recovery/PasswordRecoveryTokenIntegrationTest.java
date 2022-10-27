package pl.azebrow.harvest.integration.recovery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.AccountRepository;
import pl.azebrow.harvest.repository.PasswordRecoveryTokenRepository;
import pl.azebrow.harvest.request.PasswordChangeRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/password_recovery_token_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Import(ClockConfig.class)
public class PasswordRecoveryTokenIntegrationTest extends BaseIntegrationTest {

    private final static String RECOVERY_URL = "/api/v1/recovery";

    @Autowired
    private PasswordRecoveryTokenRepository tokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldCreateRecoveryToken() throws Exception {
        mockMvc.perform(post(RECOVERY_URL)
                        .param("email", "user1@test.qp"))
                .andExpect(status().isOk());
        var account = accountRepository.getReferenceById(1L);
        var token = tokenRepository.findByAccount(account);
        assertTrue(token.isPresent());
        assertEquals("user1@test.qp", token.get().getAccount().getEmail());
        assertEquals(LocalDateTime.parse("2022-10-22T22:25:00"), token.get().getExpiryDate());
    }

    @Test
    public void shouldNotRecoverPassword_whenTokenExpired() throws Exception {
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setPassword("PasswordStrong!");
        mockMvc.perform(post(RECOVERY_URL + "/token1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(passwordChangeRequest)))
                .andExpect(status().isBadRequest());
        var account = accountRepository.findByEmail("user1@test.qp");
        assertTrue(account.isPresent());
        assertNull(account.get().getPassword());
        assertFalse(tokenRepository.existsById(1L));
    }

    @Test
    public void shouldRecoverPassword() throws Exception {
        var rawPassword = "PasswordStrong!";
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setPassword(rawPassword);
        mockMvc.perform(post(RECOVERY_URL + "/token2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(passwordChangeRequest)))
                .andExpect(status().isOk());
        var account = accountRepository.findByEmail("user2@test.qp");
        assertTrue(account.isPresent());
        assertNotNull(account.get().getPassword());
        assertTrue(passwordEncoder.matches(rawPassword, account.get().getPassword()));
        assertFalse(tokenRepository.existsById(2L));
    }
}
