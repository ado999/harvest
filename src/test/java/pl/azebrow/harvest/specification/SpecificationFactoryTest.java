package pl.azebrow.harvest.specification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SpecificationFactoryTest {

    SpecificationFactory factory;

    @BeforeEach
    void setup() {
        factory = new SpecificationFactory();
    }

    @Test
    void returnsSpecification() {
        assertInstanceOf(JobSpecification.class, factory.toSpecification(mock(SearchCriteria.class), Job.class));
        assertInstanceOf(PaymentSpecification.class, factory.toSpecification(mock(SearchCriteria.class), Payment.class));
    }
}