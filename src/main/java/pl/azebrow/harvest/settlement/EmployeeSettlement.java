package pl.azebrow.harvest.settlement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSettlement {
    private Employee employee;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Collection<Job> jobs;
    private BigDecimal jobsAmount;
    private Collection<Payment> payments;
    private BigDecimal paymentsAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalBalance;
}
