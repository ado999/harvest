package pl.azebrow.harvest.integration.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.JobRepository;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/job_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
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
    @WithUserDetails("test@harvest.qp")
    public void shouldPostJob() throws Exception {
        var jobRequest = new JobRequestBuilder()
                .employeeId(1L)
                .jobTypeId(1L)
                .rate(BigDecimal.valueOf(123L))
                .quantity(BigDecimal.valueOf(2L))
                .jobRequest();
        var jobValue = jobRequest.getRate().multiply(jobRequest.getQuantity());
        mockMvc.perform(post(JOB_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(jobRequest)))
                .andExpect(status().isCreated());
        var job = jobRepository.findById(4L);
        assertEquals(4, jobRepository.count());
        assertTrue(job.isPresent());
        assertEquals(jobRequest.getJobTypeId(), job.get().getJobType().getId());
        assertEquals(jobRequest.getEmployeeId(), job.get().getEmployee().getId());
        assertThat(jobValue, comparesEqualTo(job.get().getTotalAmount()));
    }

    @Test
    @WithUserDetails("test@harvest.qp")
    public void shouldUpdateJob() throws Exception {
        var jobRequest = new JobRequestBuilder()
                .employeeId(1L)
                .jobTypeId(1L)
                .rate(BigDecimal.valueOf(2L))
                .quantity(BigDecimal.valueOf(3L))
                .jobRequest();
        var jobValue = jobRequest.getRate().multiply(jobRequest.getQuantity());
        mockMvc.perform(put(JOB_URL + "/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(jobRequest)))
                .andExpect(status().isNoContent());
        var job = jobRepository.findById(1L);
        assertTrue(job.isPresent());
        assertEquals(jobRequest.getJobTypeId(), job.get().getJobType().getId());
        assertEquals(jobRequest.getEmployeeId(), job.get().getEmployee().getId());
        assertThat(jobValue, comparesEqualTo(job.get().getTotalAmount()));
    }
}
