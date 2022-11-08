package pl.azebrow.harvest.integration.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.PaymentRepository;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/payment_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PaymentIntegrationTest extends BaseIntegrationTest {

    private final static String PAYMENT_URL = "/api/v1/payment";

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @WithUserDetails("staff@test.qp")
    public void shouldCreatePayment() throws Exception {
        var paymentRequest = new PaymentRequestBuilder()
                .employeeId(1L)
                .amount(BigDecimal.valueOf(50L))
                .paymentRequest();
        mockMvc.perform(post(PAYMENT_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(paymentRequest)))
                .andExpect(status().isCreated());
        var payment = paymentRepository.findById(6L);
        assertTrue(payment.isPresent());
        assertEquals(paymentRequest.getEmployeeId(), payment.get().getEmployee().getId());
        assertEquals(3, payment.get().getPayer().getId());
        assertThat(paymentRequest.getAmount().negate(), comparesEqualTo(payment.get().getAmount()));
    }

    @Test
    @WithUserDetails("staff@test.qp")
    public void shouldUpdatePayment() throws Exception {
        var paymentRequest = new PaymentRequestBuilder()
                .employeeId(2L)
                .amount(BigDecimal.valueOf(10L))
                .paymentRequest();
        mockMvc.perform(put(PAYMENT_URL + "/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(paymentRequest)))
                .andExpect(status().isNoContent());
        var payment = paymentRepository.findById(1L);
        assertTrue(payment.isPresent());
        assertEquals(paymentRequest.getEmployeeId(), payment.get().getEmployee().getId());
        assertThat(paymentRequest.getAmount().negate(), comparesEqualTo(payment.get().getAmount()));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnPayment() throws Exception {
        mockMvc.perform(get(PAYMENT_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.employee.id").value("1"))
                .andExpect(jsonPath("$.payer.id").value("3"))
                .andExpect(jsonPath("$.amount").value("-100.0"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnPaymentsByEmployeeId() throws Exception {
        mockMvc.perform(get(PAYMENT_URL + "/employee/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

}
