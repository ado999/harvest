package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Insurance;
import pl.azebrow.harvest.repository.InsuranceRepository;
import pl.azebrow.harvest.request.InsuranceRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final EmployeeService employeeService;
    private final InsuranceRepository insuranceRepository;

    public List<Insurance> getEmployeePolicies(Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return new ArrayList<>(insuranceRepository
                .findAllByEmployee(employee));
    }

    public void addPolicy(InsuranceRequest insuranceRequest) {
        Employee employee = employeeService.getEmployeeById(insuranceRequest.getEmployeeId());
        Insurance insurance = Insurance.builder()
                .employee(employee)
                .validFrom(insuranceRequest.getValidFrom())
                .validTo(insuranceRequest.getValidTo())
                .build();
        insuranceRepository.save(insurance);
    }

    public void removePolicy(Long id) {
        Insurance insurance = findInsurance(id);
        insuranceRepository.delete(insurance);
    }

    private Insurance findInsurance(Long insuranceId) {
        return insuranceRepository
                .findById(insuranceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Insurance with id \"%d\" not found!", insuranceId));
    }
}
