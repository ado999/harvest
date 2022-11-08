package pl.azebrow.harvest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Insurance;

import java.util.Collection;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Page<Insurance> findAllByEmployee(Employee employee, Pageable pageable);
    Collection<Insurance> findAllByEmployee(Employee employee);
}
