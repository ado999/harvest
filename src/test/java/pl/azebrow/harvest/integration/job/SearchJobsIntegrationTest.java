package pl.azebrow.harvest.integration.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.JobRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.azebrow.harvest.integration.job.JobSearchKeys.*;

@SqlGroup({
        @Sql(scripts = "classpath:db/job_search_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class SearchJobsIntegrationTest extends BaseIntegrationTest {

    private final static String JOB_URL = "/api/v1/job";

    @Autowired
    private JobRepository jobRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByLocation() throws Exception {
        String locationId = "1";
        mockMvc.perform(get(JOB_URL)
                        .param(LOCATION, locationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(6)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByEmployee() throws Exception {
        String employeeId = "1";
        mockMvc.perform(get(JOB_URL)
                        .param(EMPLOYEE, employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByJobType() throws Exception {
        String jobTypeId = "1";
        mockMvc.perform(get(JOB_URL)
                        .param(JOB_TYPE, jobTypeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(7)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByApprover() throws Exception {
        String approverId = "5";
        mockMvc.perform(get(JOB_URL)
                        .param(APPROVER, approverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByDateFrom() throws Exception {
        String dateFrom = "2022-06-08";
        mockMvc.perform(get(JOB_URL)
                        .param(DATE_FROM, dateFrom))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(10)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByDateTo() throws Exception {
        String dateTo = "2022-06-07";
        mockMvc.perform(get(JOB_URL)
                        .param(DATE_TO, dateTo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsBetweenDates() throws Exception {
        String dateFrom = "2022-06-07";
        String dateTo = "2022-06-08";
        mockMvc.perform(get(JOB_URL)
                        .param(DATE_FROM, dateFrom)
                        .param(DATE_TO, dateTo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsBetweenDatesAndLocation() throws Exception {
        String dateFrom = "2022-06-07";
        String dateTo = "2022-06-08";
        String locationId = "1";
        mockMvc.perform(get(JOB_URL)
                        .param(DATE_FROM, dateFrom)
                        .param(DATE_TO, dateTo)
                        .param(LOCATION, locationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByEmployeeAndApprover() throws Exception {
        String employeeId = "1";
        String approverId = "4";
        mockMvc.perform(get(JOB_URL)
                        .param(EMPLOYEE, employeeId)
                        .param(APPROVER, approverId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterJobsByJobTypeAndLocation() throws Exception {
        String jobTypeId = "1";
        String locationId = "1";
        mockMvc.perform(get(JOB_URL)
                        .param(JOB_TYPE, jobTypeId)
                        .param(LOCATION, locationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldUseAllFilters() throws Exception {
        String locationId = "2";
        String employeeId = "1";
        String jobTypeId = "2";
        String approverId = "5";
        String dateFrom = "2022-06-12";
        String dateTo = "2022-06-13";
        mockMvc.perform(get(JOB_URL)
                        .param(LOCATION, locationId)
                        .param(EMPLOYEE, employeeId)
                        .param(JOB_TYPE, jobTypeId)
                        .param(APPROVER, approverId)
                        .param(DATE_FROM, dateFrom)
                        .param(DATE_TO, dateTo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFilterOutAllJobs() throws Exception {
        String locationId = "1";
        String employeeId = "1";
        String jobTypeId = "2";
        String approverId = "4";
        String dateFrom = "2022-06-07";
        String dateTo = "2022-06-13";
        mockMvc.perform(get(JOB_URL)
                        .param(LOCATION, locationId)
                        .param(EMPLOYEE, employeeId)
                        .param(JOB_TYPE, jobTypeId)
                        .param(APPROVER, approverId)
                        .param(DATE_FROM, dateFrom)
                        .param(DATE_TO, dateTo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldNotFilterOutAnyJobs() throws Exception {
        mockMvc.perform(get(JOB_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(12)));
    }

}
