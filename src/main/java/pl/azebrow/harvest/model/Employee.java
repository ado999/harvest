package pl.azebrow.harvest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "UK_employee_code", columnNames = "code")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @OneToOne(mappedBy = "employee")
    private Account account;

    @OneToMany(mappedBy = "employee")
    private Collection<Insurance> policies;

    @OneToMany(mappedBy = "employee")
    private Collection<Payment> payments;

    private String phoneNumber;

    private Boolean passportTaken;

}
