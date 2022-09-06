package pl.azebrow.harvest.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotNull
    @Min(1)
    private Long employeeId;

    @NotNull
    @Positive
    private BigDecimal amount;
}
