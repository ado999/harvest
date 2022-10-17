package pl.azebrow.harvest.integration.job;

import org.springframework.beans.factory.annotation.Autowired;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.JobRepository;

public class SearchJobsIntegrationTest extends BaseIntegrationTest {

    private final static String JOB_URL = "/api/v1/job";

    @Autowired
    private JobRepository jobRepository;


}
