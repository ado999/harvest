package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountEmailUpdateRequestTest extends ValidatorTest {
    AccountEmailUpdateRequest request;

    @BeforeEach
    void setup() {
        request = new AccountEmailUpdateRequest();
    }

    @Test
    void returnsViolations() {
        request.setEmail("invalid@email");
        var violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setEmail("valid@email.qp");
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}