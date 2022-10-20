package pl.azebrow.harvest.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public abstract class ValidatorTest {
    protected static ValidatorFactory validatorFactory;
    protected static Validator validator;

    @BeforeAll
    protected static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    protected static void close() {
        validatorFactory.close();
    }

    protected <T> Set<ConstraintViolation<T>> validate(T request) {
        return validator.validate(request);
    }
}
