package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JobRequestTest extends ValidatorTest {
    JobRequest request;

    @BeforeEach
    void setup(){
        request = new JobRequest();
    }

    @Test
    void returnsViolations() {
        request.setJobTypeId(0L);
        request.setEmployeeId(null);
        request.setLocationId(-1L);
        request.setQuantity(BigDecimal.ZERO);
        request.setRate(BigDecimal.ONE.negate());
        var violations = validator.validate(request);
        assertEquals(5, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setJobTypeId(1L);
        request.setEmployeeId(2L);
        request.setLocationId(3L);
        request.setQuantity(BigDecimal.ONE);
        request.setRate(BigDecimal.TEN);
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}