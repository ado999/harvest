package pl.azebrow.harvest.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class JobTypeRequest {
    private String title;
    private String jobUnit;
    private BigDecimal defaultRate;
}
