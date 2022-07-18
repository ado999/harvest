package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.exeption.UserNotFoundException;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Insurance;
import pl.azebrow.harvest.repository.EmployeeRepository;
import pl.azebrow.harvest.repository.InsuranceRepository;
import pl.azebrow.harvest.request.InsuranceRequest;
import pl.azebrow.harvest.response.InsuranceResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final EmployeeRepository employeeRepository;
    private final InsuranceRepository insuranceRepository;

    private final ModelMapper mapper;


    public List<InsuranceResponse> getEmployeePolicies(Long employeeId) {
        Employee employee = findEmployee(employeeId);
        return insuranceRepository
                .findAllByEmployee(employee)
                .stream()
                .map(p -> mapper.map(p, InsuranceResponse.class))
                .collect(Collectors.toList());
    }

    public void addPolicy(InsuranceRequest insuranceRequest) {
        Employee employee = findEmployee(insuranceRequest.getEmployeeId());
        Insurance insurance = Insurance.builder()
                .employee(employee)
                .validFrom(insuranceRequest.getValidFrom())
                .validTo(insuranceRequest.getValidTo())
                .build();
        insuranceRepository.save(insurance);
    }

    public void removePolicy(InsuranceRequest insuranceRequest) {
        Insurance insurance = findInsurance(insuranceRequest.getInsuranceId());
        insuranceRepository.delete(insurance);
    }

    private Employee findEmployee(Long employeeId) {
        return employeeRepository
                .findById(employeeId)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("Employee with id \"%d\" not found!", employeeId)));
    }

    private Insurance findInsurance(Long insuranceId) {
        return insuranceRepository
                .findById(insuranceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Insurance with id \"%d\" not found!", insuranceId));
    }
}
