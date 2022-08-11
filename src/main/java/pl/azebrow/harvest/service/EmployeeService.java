package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.EmployeeNotFoundException;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee getEmployeeByCode(String code) {
        return employeeRepository
                .findByCode(code)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(String.format("Employee with code \"%s\" not found!", code))
                );
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(String.format("Employee with id \"%d\" not found!", id))
                );
    }
}
