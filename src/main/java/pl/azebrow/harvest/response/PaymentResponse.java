package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long id;
    private EmployeeResponse employee;
    private AccountResponse payer;
    private LocalDateTime createdDate;
    private BigDecimal amount;
}
