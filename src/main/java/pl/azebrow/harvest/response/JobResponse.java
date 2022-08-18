package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class JobResponse {
    private Long id;
    private LocalDateTime date;
    private Long locationId;
    private Long employeeId;
    private JobTypeResponse jobType;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal totalAmount;
    private AccountResponse approver;

}
