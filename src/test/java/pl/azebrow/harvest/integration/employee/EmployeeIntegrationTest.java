package pl.azebrow.harvest.integration.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.EmployeeRepository;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAllEmployees() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL)
                        .param("showDisabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
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
        mockMvc.perform(get(EMPLOYEE_URL + "/id/69420"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByFirstName() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/MARCI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByLastName() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/smitherman"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByEmail() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/msmitherman1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldFindEmployeeByCode() throws Exception {
        mockMvc.perform(get(EMPLOYEE_URL + "/search/tin0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldCreateEmployee() throws Exception {
        var employeeRequest = new EmployeeRequestBuilder()
                .firstName("NewEmployee")
                .employeeRequest();
        mockMvc.perform(post(EMPLOYEE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(employeeRequest)))
                .andExpect(status().isCreated());
        assertEquals(11, employeeRepository.count());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldNotCreateEmployeeWithDuplicateEmail() throws Exception {
        var employeeRequest = new EmployeeRequestBuilder().email("ledge5@umn.edu").employeeRequest();
        mockMvc.perform(post(EMPLOYEE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(employeeRequest)))
                .andExpect(status().isConflict());
        assertEquals(10, employeeRepository.count());
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
