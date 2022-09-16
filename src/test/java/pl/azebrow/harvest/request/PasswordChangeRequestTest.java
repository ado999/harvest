package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordChangeRequestTest extends ValidatorTest {

    PasswordChangeRequest request;

    @BeforeEach
    void setup() {
        request = new PasswordChangeRequest();
    }

    @Test
    void returnsViolations() {
        request.setPassword("short");
        var violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setPassword("long_pwd");
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}