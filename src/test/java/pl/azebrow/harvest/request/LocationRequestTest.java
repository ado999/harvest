package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationRequestTest extends ValidatorTest {

    LocationRequest request;

    @BeforeEach
    void setup() {
        request = new LocationRequest();
    }

    @Test
    void returnsViolations() {
        request.setOwner(0L);
        request.setDescription("");
        request.setDisabled(null);
        var violations = validator.validate(request);
        assertEquals(3, violations.size());
    }

    @Test
    void returnsNoViolations() {
        request.setOwner(123L);
        request.setDescription("Description");
        request.setDisabled(false);
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}