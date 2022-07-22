package pl.azebrow.harvest.response;

import java.math.BigDecimal;

public class JobResponse {
    private Long id;
    private LocationResponse location;
    private EmployeeShortResponse employee;
    private String jobTypeTitle;
    private String jobTypeUnit;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal totalAmount;

}
