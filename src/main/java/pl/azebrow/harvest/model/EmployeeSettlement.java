package pl.azebrow.harvest.model;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Immutable
public class EmployeeSettlement {

    @Id
    private Long id;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Location location;
    @ManyToOne
    private Account account;
    private BigDecimal totalAmount;
    private BigDecimal balance;
    private LocalDateTime date;
}
