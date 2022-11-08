package pl.azebrow.harvest.integration.login;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/login.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class LoginTest extends BaseIntegrationTest {

    private final static String LOGIN_URL = "/login";

    private final static String USER = "test@harvest.qp";
    private final static String PASS = "janusz";

    @Test
    public void shouldSuccessfullyLogin() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", USER)
                        .param("password", PASS)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotSuccessfullyLoginWhenInvalidPassword() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", USER)
                        .param("password", "Invalid")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

}
