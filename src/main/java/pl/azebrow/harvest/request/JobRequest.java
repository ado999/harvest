package pl.azebrow.harvest.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class JobRequest {

    @NotNull
    @Min(1)
    private Long locationId;

    @NotNull
    @Min(1)
    private Long employeeId;

    @NotNull
    @Min(1)
    private Long jobTypeId;

    @NotNull
    @Positive
    private BigDecimal quantity;

    @NotNull
    @Positive
    private BigDecimal rate;
}
