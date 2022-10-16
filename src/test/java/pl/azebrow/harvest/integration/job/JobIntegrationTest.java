package pl.azebrow.harvest.integration.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.JobRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobIntegrationTest extends BaseIntegrationTest {

    private final static String JOB_URL = "/api/v1/job";

    @Autowired
    private JobRepository jobRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnJobById() throws Exception {
        mockMvc.perform(get(JOB_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void test() throws Exception {
    }


}
