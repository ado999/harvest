package pl.azebrow.harvest.settlement.datasource;

import lombok.Getter;
import pl.azebrow.harvest.settlement.LocationsSettlement;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class LocationsSettlementDatasource {
    private final String generationTime;
    private final String generationDate;
    private final String dateFrom;
    private final String dateTo;
    private final Collection<LocationSettlementDatasource> locationSettlements;
    private final BigDecimal totalAmount;

    public LocationsSettlementDatasource(LocationsSettlement settlement) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        generationTime = settlement.getReportGenerationTime().format(timeFormat);
        generationDate = settlement.getReportGenerationTime().format(dateFormat);
        dateFrom = settlement.getDateFrom().format(dateFormat);
        dateTo = settlement.getDateTo().format(dateFormat);
        locationSettlements = settlement.getLocationSettlements()
                .stream()
                .map(LocationSettlementDatasource::new)
                .collect(Collectors.toList());
        totalAmount = settlement.getTotalAmount();
    }
}
