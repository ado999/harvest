package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRequestTest extends ValidatorTest {

    PaymentRequest request;

    @BeforeEach
    void setUp() {
        request = new PaymentRequest();
    }

    @Test
    void returnsViolations() {
        request.setEmployeeId(null);
        request.setAmount(null);
        var violations = validator.validate(request);
        assertEquals(2, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setEmployeeId(123L);
        request.setAmount(BigDecimal.TEN);
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}