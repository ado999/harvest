package pl.azebrow.harvest.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long employeeId;
    private BigDecimal amount;
}
