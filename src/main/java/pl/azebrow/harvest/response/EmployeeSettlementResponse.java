package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmployeeSettlementResponse {
    private Long employeeId;
    private Long locationId;
    private Long accountId;
    private BigDecimal totalAmount;
    private BigDecimal balance;
    private LocalDateTime date;
}
