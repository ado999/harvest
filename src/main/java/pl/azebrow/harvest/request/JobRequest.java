package pl.azebrow.harvest.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class JobRequest {

    private Long locationId;
    private Long employeeId;
    private Long jobTypeId;
    private BigDecimal quantity;
    private BigDecimal rate;
}
