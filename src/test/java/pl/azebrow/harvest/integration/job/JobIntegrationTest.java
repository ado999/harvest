package pl.azebrow.harvest.integration.job;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.JobType;
import pl.azebrow.harvest.repository.EmployeeRepository;
import pl.azebrow.harvest.repository.JobRepository;
import pl.azebrow.harvest.repository.JobTypeRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobIntegrationTest extends BaseIntegrationTest {

    private final static String JOB_URL = "/api/v1/job";
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnJobById() throws Exception {
        mockMvc.perform(get(JOB_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithUserDetails("janusz@admin.qp")
    public void shouldPostJob() throws Exception {
        var jobRequest = new JobRequestBuilder()
                .employeeId(getExistingEmployee().getId())
                .jobTypeId(getExistingJobType().getId())
                .rate(BigDecimal.valueOf(123L))
                .quantity(BigDecimal.valueOf(2L))
                .jobRequest();
        var jobValue = jobRequest.getRate().multiply(jobRequest.getQuantity());
        mockMvc.perform(post(JOB_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(jobRequest)))
                .andExpect(status().isCreated());
        var job = jobRepository.findAll()
                .stream().filter(j -> j.getRate().equals(jobRequest.getRate()))
                .findFirst();
        assertTrue(job.isPresent());
        assertEquals(jobRequest.getJobTypeId(), job.get().getJobType().getId());
        assertEquals(jobRequest.getEmployeeId(), job.get().getEmployee().getId());
        assertEquals(jobValue, job.get().getTotalAmount());
    }

    @Test
    @WithUserDetails("janusz@admin.qp")
    public void shouldUpdateJob() throws Exception {
        var jobRequest = new JobRequestBuilder()
                .employeeId(getExistingEmployee().getId())
                .jobTypeId(getExistingJobType().getId())
                .rate(BigDecimal.valueOf(2L))
                .quantity(BigDecimal.valueOf(3L))
                .jobRequest();
        var jobValue = jobRequest.getRate().multiply(jobRequest.getQuantity());
        mockMvc.perform(put(JOB_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(jobRequest)))
                .andExpect(status().isNoContent());
        var job = jobRepository.findById(1L);
        assertTrue(job.isPresent());
        assertEquals(jobRequest.getJobTypeId(), job.get().getJobType().getId());
        assertEquals(jobRequest.getEmployeeId(), job.get().getEmployee().getId());
        assertEquals(jobValue, job.get().getTotalAmount());
    }

    public JobType getExistingJobType(){
        var jobType = jobTypeRepository.findAll().stream()
                .findAny();
        return jobType.orElseThrow(() -> new RuntimeException("Could not find job type"));
    }

    public Employee getExistingEmployee(){
        var employee = employeeRepository.findAll().stream()
                .findAny();
        return employee.orElseThrow(() -> new RuntimeException("Could not find employee"));
    }


}
