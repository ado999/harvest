package pl.azebrow.harvest.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceResponse {

    private Long id;
    private LocalDate validFrom;
    private LocalDate validTo;

}
