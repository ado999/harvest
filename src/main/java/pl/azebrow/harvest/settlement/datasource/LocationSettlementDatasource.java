package pl.azebrow.harvest.settlement.datasource;

import lombok.Getter;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.settlement.LocationSettlement;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class LocationSettlementDatasource {
    private final String locationName;
    private final Collection<JobDatasource> jobs;
    private final BigDecimal amount;

    public LocationSettlementDatasource(LocationSettlement settlement) {
        this.locationName = settlement.getLocation().getDescription();
        this.jobs = settlement.getJobs().stream()
                .map(JobDatasource::new)
                .collect(Collectors.toList());
        this.amount = settlement.getJobs().stream()
                .map(Job::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
