package pl.azebrow.harvest.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
public class JobTypeRequest {
    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    @Size(min = 1)
    private String jobUnit;

    @PositiveOrZero
    private BigDecimal defaultRate;
}
