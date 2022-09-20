package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmployeeSettlementResponse {
    private EmployeeResponse employee;
    private LocationResponse location;
    private AccountResponse account;
    private BigDecimal totalAmount;
    private BigDecimal balance;
    private LocalDateTime date;
}
