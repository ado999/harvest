package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JobTypeRequestTest extends ValidatorTest {

    JobTypeRequest request;

    @BeforeEach
    void setup() {
        request = new JobTypeRequest();
    }

    @Test
    void returnsViolations() {
        request.setJobUnit("");
        request.setTitle(null);
        request.setDefaultRate(BigDecimal.ONE.negate());
        assertEquals(3, validate(request).size());
    }

    @Test
    void returnsNoViolations() {
        request.setJobUnit("Unit");
        request.setTitle("Title");
        request.setDefaultRate(BigDecimal.ZERO);
        assertEquals(0, validate(request).size());
    }
}