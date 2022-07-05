package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.EmployeeNotFoundException;
import pl.azebrow.harvest.repository.EmployeeRepository;
import pl.azebrow.harvest.response.EmployeeResponse;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper mapper;

    public EmployeeResponse findEmployeeByCode(String code) {
        return employeeRepository
                .findByCode(code)
                .map(e -> mapper.map(e, EmployeeResponse.class))
                .orElseThrow(
                        () -> new EmployeeNotFoundException(String.format("Employee with code \"%s\" not found!", code))
                );
    }
}
