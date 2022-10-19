package pl.azebrow.harvest.integration.job;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.JobRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchJobsIntegrationTest extends BaseIntegrationTest {

    private final static String JOB_URL = "/api/v1/job";

    @Autowired
    private JobRepository jobRepository;

    @BeforeAll
    public void setup() {

    }


}
