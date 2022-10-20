package pl.azebrow.harvest.integration.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.EmployeeRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/employee_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class EmployeeIntegrationTest extends BaseIntegrationTest {

    private final static String EMPLOYEE_URL = "/api/v1/employee";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnEnabledEmployees() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAllEmployees() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL)
                        .param("showDisabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnEmployeeByCode() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/code/SMI22222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account.firstName").value("Marci"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldNotReturnEmployeeByCode() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/code/NONEXISTENT"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnEmployeeById() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/id/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account.firstName").value("Marci"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldNotReturnEmployeeById() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/id/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByFirstName() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/MARCI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByLastName() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/smitherman"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByEmail() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/msmitherman1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByCode() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/tof5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldCreateEmployee() throws Exception {
        var employeeRequest = new EmployeeRequestBuilder()
                .employeeRequest();
        mockMvc.perform(post(EMPLOYEE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(employeeRequest)))
                .andExpect(status().isCreated());
        assertEquals(6, employeeRepository.count());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldNotCreateEmployeeWithDuplicateEmail() throws Exception {
        var employeeRequest = new EmployeeRequestBuilder()
                .email("jtofanini4@angelfire.com").employeeRequest();
        mockMvc.perform(post(EMPLOYEE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(employeeRequest)))
                .andExpect(status().isConflict());
        assertEquals(5, employeeRepository.count());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldUpdateEmployee() throws Exception {
        var updateRequest = new EmployeeRequestBuilder()
                .phoneNumber("123123123")
                .passportTaken(true)
                .updateRequest();
        mockMvc.perform(put(EMPLOYEE_URL + "/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(updateRequest)))
                .andExpect(status().isNoContent());
        var employee = employeeRepository.findById(1L);
        assertTrue(employee.isPresent());
        assertEquals(updateRequest.getPhoneNumber(), employee.get().getPhoneNumber());
        assertEquals(updateRequest.getPassportTaken(), employee.get().getPassportTaken());
    }

}
