package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.azebrow.harvest.model.Employee;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCode(String code);

    Boolean existsByCode(String code);

    @Query("SELECT e FROM Employee e " +
            "WHERE UPPER(CONCAT(e.account.firstName, e.account.lastName, e.account.email, e.code))" +
            "LIKE UPPER(CONCAT('%', :query, '%'))")
    Collection<Employee> findAllByQuery(String query);
}
