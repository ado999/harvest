package pl.azebrow.harvest.specification;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import pl.azebrow.harvest.model.Job;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static pl.azebrow.harvest.specification.SpecificationBuilder.LOCATION;

class JobSpecificationTest {

    @Mock
    Root<Job> root;

    @Mock
    CriteriaQuery<?> query;

    @Mock
    CriteriaBuilder cb;


    @Test
    void returnsIdPredicate() {
        var locationCriteria = new SearchCriteria(LOCATION, 1L);
        var employeeCriteria = new SearchCriteria(LOCATION, 2L);
        var jobTypeCriteria = new SearchCriteria(LOCATION, 3L);
        var approverCriteria = new SearchCriteria(LOCATION, 4L);
        var locationSpec = new JobSpecification(locationCriteria);
        var employeeSpec = new JobSpecification(employeeCriteria);
        var jobTypeSpec = new JobSpecification(jobTypeCriteria);
        var approverSpec = new JobSpecification(approverCriteria);
        assertTrue(true);
        //todo remove or implement
    }
}