package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountUpdateRequestTest extends ValidatorTest {

    AccountUpdateRequest request;

    @BeforeEach
    void setup() {
        request = new AccountUpdateRequest();
    }

    @Test
    void returnsViolations(){
        request.setEnabled(null);
        request.setFirstName("");
        request.setLastName(null);
        var violations = validator.validate(request);
        assertEquals(4, violations.size());
    }

    @Test
    void returnsNoViolations(){
        request.setEnabled(true);
        request.setFirstName("First");
        request.setLastName("Last");
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}