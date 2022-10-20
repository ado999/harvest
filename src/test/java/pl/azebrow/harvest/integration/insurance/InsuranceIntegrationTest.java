package pl.azebrow.harvest.integration.insurance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.EmployeeRepository;
import pl.azebrow.harvest.repository.InsuranceRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/insurance_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class InsuranceIntegrationTest extends BaseIntegrationTest {

    private final static String INSURANCE_URL = "/api/v1/insurance";

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnEmployeePolicies() throws Exception {
        mockMvc.perform(get(INSURANCE_URL + "/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldAddPolicy() throws Exception {
        var insuranceRequestBuilder = new InsuranceRequestBuilder()
                .employeeId(1L);
        var insuranceRequest = insuranceRequestBuilder.insuranceRequest();
        mockMvc.perform(post(INSURANCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(insuranceRequest)))
                .andExpect(status().isCreated());
        var employee = employeeRepository.getReferenceById(insuranceRequest.getEmployeeId());
        var insurances = insuranceRepository.findAllByEmployee(employee);
        assertTrue(insurances.stream()
                .anyMatch(i -> i.getValidFrom().equals(insuranceRequestBuilder.getValidFrom()) && i.getValidTo().equals(insuranceRequestBuilder.getValidTo())));
        assertEquals(4, insurances.size());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldDeletePolicy() throws Exception {
        mockMvc.perform(delete(INSURANCE_URL + "/1"))
                .andExpect(status().isNoContent());
        assertFalse(insuranceRepository.existsById(1L));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldNotFindPolicy() throws Exception {
        mockMvc.perform(delete(INSURANCE_URL + "/999999"))
                .andExpect(status().isNotFound());
    }

}
