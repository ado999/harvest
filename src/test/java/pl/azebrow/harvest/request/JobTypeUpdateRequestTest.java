package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobTypeUpdateRequestTest extends ValidatorTest {

    JobTypeUpdateRequest request;

    @BeforeEach
    void setUp() {
        request = new JobTypeUpdateRequest();
    }

    @Test
    void returnsViolations() {
        request.setDisabled(null);
        var violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setDisabled(true);
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}