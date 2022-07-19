package pl.azebrow.harvest.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@Builder
public class JobType {

    @Id
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private JobUnit unit;

    private BigDecimal defaultRate;
}
