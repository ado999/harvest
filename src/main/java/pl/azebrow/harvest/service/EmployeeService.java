package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.exeption.EmployeeNotFoundException;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.repository.EmployeeRepository;
import pl.azebrow.harvest.request.EmployeeRequest;
import pl.azebrow.harvest.request.EmployeeUpdateRequest;
import pl.azebrow.harvest.utils.EmployeeCodeGenerator;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeCodeGenerator employeeCodeGenerator;

    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = Employee.builder()
                .code(employeeCodeGenerator.generateCode(request.getLastName()))
                .passportTaken(request.getPassportTaken())
                .phoneNumber(request.getPhoneNumber())
                .balance(BigDecimal.ZERO)
                .build();
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    public void updateEmployee(Long id, EmployeeUpdateRequest updateRequest) {
        var employee = getEmployeeById(id);
        employee.setPassportTaken(updateRequest.getPassportTaken());
        employee.setPhoneNumber(updateRequest.getPhoneNumber());
        employeeRepository.save(employee);
    }

    public Employee getEmployeeByCode(String code) {
        return employeeRepository
                .findByCode(code)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(String.format("Employee with code \"%s\" not found!", code))
                );
    }

    public void updateEmployeeBalance(Long id, BigDecimal amount) {
        var employee = getEmployeeById(id);
        var oldBalance = employee.getBalance();
        var newBalance = oldBalance.add(amount);
        employee.setBalance(newBalance);
        employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(String.format("Employee with id \"%d\" not found!", id))
                );
    }

    public Page<Employee> getEmployeePage(boolean showDisabled, String query, Pageable pageable) {
        var skipDisabled = !showDisabled;
        if (query.isEmpty()) {
            return employeeRepository.findAllWithDisabledFilter(skipDisabled, pageable);
        }
        return employeeRepository.findAllByQuery(skipDisabled, query, pageable);
    }
}
