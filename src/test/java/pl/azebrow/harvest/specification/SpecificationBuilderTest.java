package pl.azebrow.harvest.specification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.azebrow.harvest.model.Job;

import static org.junit.jupiter.api.Assertions.*;

class SpecificationBuilderTest {

    SpecificationBuilder builder;

    @BeforeEach
    void setup() {
        builder = new SpecificationBuilder();
    }

    @Test
    void returnsNullWhenCriteriaListEmpty() {
        assertNull(builder.build(Job.class));
    }

    @Test
    void returnsSpecification() {
        builder.with(SpecificationBuilder.EMPLOYEE, 123L);
        assertNotNull(builder.build(Job.class));
    }
}