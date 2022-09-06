package pl.azebrow.harvest.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class InsuranceRequest {
    @NotNull
    @Min(1)
    private Long employeeId;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;
}
