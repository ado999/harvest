package pl.azebrow.harvest.settlement;

import lombok.Getter;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Location;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
public class LocationSettlement {
    private final Location location;
    private final Collection<Job> jobs;
    private final BigDecimal amount;

    public LocationSettlement(Location location, Collection<Job> jobs) {
        this.location = location;
        this.jobs = jobs;
        this.amount = jobs.stream()
                .map(Job::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
