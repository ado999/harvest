package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceRequestTest extends ValidatorTest {

    InsuranceRequest request;

    @BeforeEach
    void setup() {
        request = new InsuranceRequest();
    }

    @Test
    void returnsViolations() {
        request.setEmployeeId(null);
        request.setValidFrom(null);
        request.setValidTo(null);
        var violations = validator.validate(request);
        assertEquals(3, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setEmployeeId(1L);
        request.setValidFrom(LocalDate.now());
        request.setValidTo(LocalDate.now().plus(30, ChronoUnit.DAYS));
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}