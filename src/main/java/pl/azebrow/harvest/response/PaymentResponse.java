package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long id;
    private Long employeeId;
    private Long payerId;
    private LocalDateTime createdDate;
    private BigDecimal amount;
}
