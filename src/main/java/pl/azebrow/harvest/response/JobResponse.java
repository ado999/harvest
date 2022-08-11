package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class JobResponse {
    private Long id;
    private LocalDateTime date;
    private LocationResponse location;
    private EmployeeResponse employee;
    private String jobTypeTitle;
    private String jobTypeUnit;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal totalAmount;

}
