package pl.azebrow.harvest.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceRequest {
    private Long employeeId;
    private Long insuranceId;
    private LocalDate validFrom;
    private LocalDate validTo;
}
