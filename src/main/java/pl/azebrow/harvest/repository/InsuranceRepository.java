package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Insurance;

import java.util.Collection;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Collection<Insurance> findAllByEmployee(Employee employee);
}
