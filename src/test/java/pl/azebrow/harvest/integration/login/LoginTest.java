package pl.azebrow.harvest.integration.login;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static pl.azebrow.harvest.integration.login.FormURLEncodedHttpEntityBuilder.PASSWORD;
import static pl.azebrow.harvest.integration.login.FormURLEncodedHttpEntityBuilder.USERNAME;

@SqlGroup({
        @Sql(scripts = "classpath:db/login.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class LoginTest extends BaseIntegrationTest {

    private final static String LOGIN_URL = "/login";

    private final static String USER = "test@harvest.qp";
    private final static String PASS = "janusz";

    @Test
    public void whenValidCredentials_thenRedirectToMain() {
        var response = attemptLogin(PASS);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertNotNull(response.getHeaders().get("Set-Cookie"));
        assertTrue(response.getHeaders().getLocation().toString().endsWith("/"));
    }

    @Test
    public void whenInvalidCredentials_thenRedirectToLogin() {
        var response = attemptLogin("Invalid");
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertNotNull(response.getHeaders().get("Set-Cookie"));
        assertTrue(response.getHeaders().getLocation().toString().endsWith("/login?error"));
    }

    private ResponseEntity<?> attemptLogin(String password) {
        var httpEntity = new FormURLEncodedHttpEntityBuilder()
                .withParam(USERNAME, USER)
                .withParam(PASSWORD, password)
                .build();
        return testRestTemplate.postForEntity(LOGIN_URL, httpEntity, String.class);
    }

}
