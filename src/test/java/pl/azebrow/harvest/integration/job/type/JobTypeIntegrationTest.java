package pl.azebrow.harvest.integration.job.type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.JobTypeRepository;
import pl.azebrow.harvest.request.JobTypeUpdateRequest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/job_type_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class JobTypeIntegrationTest extends BaseIntegrationTest {

    private final static String JOB_TYPE_URL = "/api/v1/job-type";

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAllJobTypes() throws Exception {
        mockMvc.perform(get(JOB_TYPE_URL)
                        .param("showDisabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAvailableJobTypes() throws Exception {
        mockMvc.perform(get(JOB_TYPE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnJobUnits() throws Exception {
        mockMvc.perform(get(JOB_TYPE_URL + "/units"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAddJobType() throws Exception {
        var request = new JobTypeRequestBuilder()
                .title("Title")
                .jobUnit("TIME")
                .defaultRate(BigDecimal.valueOf(100))
                .jobTypeRequest();
        mockMvc.perform(post(JOB_TYPE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(request)))
                .andExpect(status().isCreated());
        var jobType = jobTypeRepository.findById(5L);
        assertTrue(jobType.isPresent());
        assertEquals(request.getTitle(), jobType.get().getTitle());
        assertEquals(request.getJobUnit(), jobType.get().getUnit().getName());
        assertThat(request.getDefaultRate(), comparesEqualTo(jobType.get().getDefaultRate()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDisableJobType() throws Exception {
        var jobTypeUpdateRequest = new JobTypeUpdateRequest();
        jobTypeUpdateRequest.setDisabled(true);
        mockMvc.perform(put(JOB_TYPE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(jobTypeUpdateRequest)))
                .andExpect(status().isNoContent());
        var jobType = jobTypeRepository.findById(1L);
        assertTrue(jobType.isPresent());
        assertEquals(true, jobType.get().getDisabled());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldEnableJobType() throws Exception {
        var jobTypeUpdateRequest = new JobTypeUpdateRequest();
        jobTypeUpdateRequest.setDisabled(false);
        mockMvc.perform(put(JOB_TYPE_URL + "/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(jobTypeUpdateRequest)))
                .andExpect(status().isNoContent());
        var jobType = jobTypeRepository.findById(4L);
        assertTrue(jobType.isPresent());
        assertEquals(false, jobType.get().getDisabled());
    }
}
