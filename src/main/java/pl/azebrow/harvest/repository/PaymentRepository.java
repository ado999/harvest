package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Payment;

import java.util.Collection;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Collection<Payment> findByEmployee(Employee employee);
}
