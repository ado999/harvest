package pl.azebrow.harvest.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobTypeResponse {

    private Long id;
    private String title;
    private JobUnitResponse unit;
    private BigDecimal defaultRate;

}
