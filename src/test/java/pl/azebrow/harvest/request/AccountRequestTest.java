package pl.azebrow.harvest.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRequestTest extends ValidatorTest {

    AccountRequest request;

    @BeforeEach
    void setup() {
        request = new AccountRequest();
    }

    @Test
    void returnsViolations(){
        request.setEmail("email");
        request.setFirstName("");
        request.setLastName(null);
        var violations = validator.validate(request);
        assertEquals(4, violations.size());
    }

    @Test
    void returnsNoViolations(){
        request.setEmail("email@valid.qp");
        request.setFirstName("First");
        request.setLastName("Last");
        var violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
}