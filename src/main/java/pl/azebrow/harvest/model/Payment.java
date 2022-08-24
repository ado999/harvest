package pl.azebrow.harvest.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_employee_balance_change"))
    private Employee employee;

    @ManyToOne
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_account_payment"))
    private Account payer;

//    @Enumerated(EnumType.STRING)
//    private PaymentType type;

    private BigDecimal amount;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;
}
