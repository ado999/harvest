package pl.azebrow.harvest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Payment;

import java.util.Collection;

public interface PaymentRepository
        extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    Page<Payment> findByEmployee(Employee employee, Pageable pageable);
}
