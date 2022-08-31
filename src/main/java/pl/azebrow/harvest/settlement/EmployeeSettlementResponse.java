package pl.azebrow.harvest.settlement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.azebrow.harvest.response.EmployeeResponse;
import pl.azebrow.harvest.response.JobResponse;
import pl.azebrow.harvest.response.PaymentResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSettlementResponse {
    private EmployeeResponse employee;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Collection<JobResponse> jobs;
    private BigDecimal jobsAmount;
    private Collection<PaymentResponse> payments;
    private BigDecimal paymentsAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalBalance;
}
