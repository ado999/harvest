package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.EmployeeSettlement;

import java.util.Collection;

public interface EmployeeSettlementRepository extends JpaRepository<EmployeeSettlement, Long> {
    Collection<EmployeeSettlement> findByEmployee(Employee employee);
}
