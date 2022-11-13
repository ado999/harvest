package pl.azebrow.harvest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.azebrow.harvest.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCode(String code);

    Boolean existsByCode(String code);

    @Query("SELECT e FROM Employee e " +
            "WHERE UPPER(CONCAT(e.account.firstName, e.account.lastName, e.account.email, e.code)) LIKE UPPER(CONCAT('%', :query, '%'))" +
            "AND (e.account.enabled = true OR e.account.enabled = :skipDisabled)")
    Page<Employee> findAllByQuery(Boolean skipDisabled, String query, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.account.enabled = true OR e.account.enabled = :skipDisabled")
    Page<Employee> findAllWithDisabledFilter(Boolean skipDisabled, Pageable pageable);
}
