package pl.azebrow.harvest.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class Job {

    @Id
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "job_type_id", referencedColumnName = "id", nullable = false)
    private JobType jobType;

    private BigDecimal quantity;

    private BigDecimal rate;

    private BigDecimal totalAmount;

    private Account approver;
}
