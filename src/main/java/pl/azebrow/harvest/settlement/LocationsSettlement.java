package pl.azebrow.harvest.settlement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationsSettlement {
    private LocalDateTime reportGenerationTime;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Collection<LocationSettlement> locationSettlements;
    private BigDecimal totalAmount;
}
