package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.model.EmployeeSettlement;
import pl.azebrow.harvest.repository.EmployeeSettlementRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final EmployeeService employeeService;

    private final EmployeeSettlementRepository employeeSettlementRepository;


    public Collection<EmployeeSettlement> getEmployeeSettlement(Long employeeId) {
        var employee = employeeService.getEmployeeById(employeeId);
        return employeeSettlementRepository
                .findByEmployee(employee);
    }
}
